package com.mrcllw.whatsapp.application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mrcllw on 10/09/17.
 */

public class FirebaseConfig {

    private static FirebaseAuth firebaseAuth;
    private static DatabaseReference databaseReference;

    public static FirebaseAuth getFirebaseAuth(){
        if(firebaseAuth != null){
            return firebaseAuth;
        } else {
            firebaseAuth = FirebaseAuth.getInstance();
            return firebaseAuth;
        }
    }

    public static DatabaseReference getFirebaseDatabaseReference(){
        if(databaseReference != null){
            return databaseReference;
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference();
            return databaseReference;
        }
    }
}
