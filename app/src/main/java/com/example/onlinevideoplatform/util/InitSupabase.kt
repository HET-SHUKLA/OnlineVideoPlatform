package com.example.onlinevideoplatform.util

import com.example.onlinevideoplatform.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

//Init Supabase
class InitSupabase {

    //Make supabase static
    companion object {
        val supabase = createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_KEY
        ) {
            install(Postgrest)
        }
    }
}