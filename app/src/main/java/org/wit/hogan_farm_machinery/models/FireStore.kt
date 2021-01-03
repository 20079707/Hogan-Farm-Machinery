package org.wit.hogan_farm_machinery.models

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.jetbrains.anko.AnkoLogger

class FireStore(val context: Context) : TractorStore, AnkoLogger {

    val tractors = ArrayList<TractorModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference

    override fun findAll(): List<TractorModel> {
        return tractors
    }

    override fun findById(id: Long): TractorModel? {
        return tractors.find { p -> p.id == id }
    }

    override fun create(tractor: TractorModel) {
        val key = db.child("users").child(userId).child("tractors").push().key
        tractor.fbId = key!!
        tractors.add(tractor)
        db.child("users").child(userId).child("tractors").child(key).setValue(tractor)
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
    }

    override fun delete(tractor: TractorModel) {
        db.child("users").child(userId).child("tractors").child(tractor.fbId).removeValue()
        tractors.remove(tractor)
    }

    override fun clear() {
        tractors.clear()
    }

    fun fetchTractors(tractorsReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(tractors) { it.getValue<TractorModel>(TractorModel::class.java) }
                tractorsReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        tractors.clear()
        db.child("users").child(userId).child("tractors").addListenerForSingleValueEvent(valueEventListener)
    }
}