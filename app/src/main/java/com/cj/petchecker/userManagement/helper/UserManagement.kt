package com.cj.petchecker.userManagement.helper

import com.cj.petchecker.frameworks.helper.AES256Util
import com.cj.petchecker.userManagement.models.UserInfoModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class UserManagement {
    companion object{
        private var userInfo: UserInfoModel? = null

        fun getUserInfo(): UserInfoModel?{
            return userInfo
        }

        fun setUserInfo(userInfo: UserInfoModel?){
            this.userInfo = userInfo
        }
    }

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun signIn(email: String, password: String, completion: (Boolean) -> Unit){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    getUserInfo { _ ->
                        completion(true)
                        return@getUserInfo
                    }
                } else{
                    completion(false)
                    return@addOnCompleteListener
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
                completion(false)
                return@addOnFailureListener
            }
    }

    fun signUp(email: String, password: String, name: String, phone: String, completion: (Boolean) -> Unit){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    db.collection("Users").document(auth.currentUser?.uid ?: "").set(hashMapOf(
                        "email" to AES256Util.encrypt(email),
                        "name" to AES256Util.encrypt(name),
                        "phone" to AES256Util.encrypt(phone)
                    )).addOnCompleteListener {
                        if(it.isSuccessful){
                            completion(true)
                            return@addOnCompleteListener
                        } else{
                            try{
                                auth.currentUser?.delete()
                            } catch(e: Exception){
                                e.printStackTrace()
                            } finally {
                                completion(false)
                                return@addOnCompleteListener
                            }
                        }
                    }
                } else{
                    completion(false)
                    return@addOnCompleteListener
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
                completion(false)
                return@addOnFailureListener
            }
    }

    fun getUserInfo(completion: (Boolean) -> Unit){
        db.collection("Users").document(auth.currentUser?.uid ?: "").get().addOnCompleteListener {
            if(it.isSuccessful){
                val document = it.result

                if(document.exists()){
                    val name = document.get("name") as? String ?: ""
                    val phone = document.get("phone") as? String ?: ""
                    val email = document.get("email") as? String ?: ""

                    setUserInfo(
                        UserInfoModel(
                            email = AES256Util.decrypt(email),
                            phone = AES256Util.decrypt(phone),
                            name = AES256Util.decrypt(name),
                            uid = auth.currentUser?.uid ?: ""
                        )
                    )

                    completion(true)
                    return@addOnCompleteListener
                } else{
                    completion(false)
                    return@addOnCompleteListener
                }
            } else{
                completion(false)
                return@addOnCompleteListener
            }
        }.addOnFailureListener {
            it.printStackTrace()
            completion(false)
            return@addOnFailureListener
        }
    }

    fun sendResetPasswordMail(email: String, completion: (Boolean) -> Unit){
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            completion(it.isSuccessful)
            return@addOnCompleteListener
        }.addOnFailureListener {
            it.printStackTrace()
            completion(false)
            return@addOnFailureListener
        }
    }
}