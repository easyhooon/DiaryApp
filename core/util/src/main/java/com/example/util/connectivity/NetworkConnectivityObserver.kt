package com.example.util.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.example.util.connectivity.ConnectivityObserver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

// device 의 network status observer
class NetworkConnectivityObserver(context: Context) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<ConnectivityObserver.Status> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(ConnectivityObserver.Status.Available) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(ConnectivityObserver.Status.Losing) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(ConnectivityObserver.Status.Lost) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(ConnectivityObserver.Status.Unavailable) }
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)
            //TODO awaitClose, distinctUntilChanged() 사용 용례 정리
            // will be basically triggered whenever flow is canceled
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
            // this function basically throw all those values that are actually the same so that
            // we are not repeating ourselves when we are sending those values for the connectivity observer status
            // 이전과 같은 상태를 내보내지 않는다(not going to send)
            // flow will change only when this status changes as well
        }.distinctUntilChanged()
    }
}
