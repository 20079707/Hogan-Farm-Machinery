package org.wit.hogan_farm_machinery.models

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import org.wit.hogan_farm_machinery.helpers.readImageFromPath
import java.io.ByteArrayOutputStream
import java.io.File

class FireStore(val context: Context) : TractorStore, AnkoLogger {

    val tractors = ArrayList<TractorModel>()
    private lateinit var userId: String
    private lateinit var db: DatabaseReference
    private lateinit var st: StorageReference

    override fun findAll(): List<TractorModel> {
        return tractors
    }

    override fun findById(id: Long): TractorModel? {
        return tractors.find { p -> p.id == id }
    }

    override fun create(tractor: TractorModel) {
        val key = db.child("users").child(userId).child("tractors").push().key
        key?.let {
            tractor.fbId = key
            tractors.add(tractor)
            db.child("users").child(userId).child("tractors").child(key).setValue(tractor)
            updateImage(tractor)
        }
    }

    override fun update(tractor: TractorModel) {
        val foundtractor: TractorModel? = tractors.find { p -> p.fbId == tractor.fbId }
        if (foundtractor != null) {
            foundtractor.make = tractor.make
            foundtractor.description = tractor.description
            foundtractor.image = tractor.image
            foundtractor.location = tractor.location
        }

        db.child("users").child(userId).child("tractors").child(tractor.fbId).setValue(tractor)
        if ((tractor.image.length) > 0 && (tractor.image[0] != 'h')) {
            updateImage(tractor)
        }
    }

    override fun delete(tractor: TractorModel) {
        db.child("users").child(userId).child("tractors").child(tractor.fbId).removeValue()
        tractors.remove(tractor)
        updateImage(tractor)

    }

    override fun clear() {
        tractors.clear()
    }

    private fun updateImage(tractor: TractorModel) {
        if (tractor.image != "") {
            val fileName = File(tractor.image)
            val imageName = fileName.name

            val imageRef = st.child("$userId/$imageName")
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, tractor.image)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        tractor.image = it.toString()
                        db.child("users").child(userId).child("tractors").child(tractor.fbId).setValue(tractor)
                    }
                }
            }

        }
    }

    fun fetchTractors(tractorsReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(tractors) { it.getValue(TractorModel::class.java) }
                tractorsReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        st = FirebaseStorage.getInstance().reference
        tractors.clear()
        db.child("users").child(userId).child("tractors").addListenerForSingleValueEvent(valueEventListener)
    }
}