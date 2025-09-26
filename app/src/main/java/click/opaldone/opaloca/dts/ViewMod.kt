package click.opaldone.opaloca.dts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import click.opaldone.opaloca.dts.Logmes

class ViewMod() : ViewModel() {
    val _logis = mutableStateListOf<Logmes>()
    val logis: List<Logmes> get() = _logis

    fun clearLogis() {
        viewModelScope.launch {
            _logis.clear()
        }
    }

    fun addLogis(msg: String) {
        viewModelScope.launch {
            _logis.add(0, Logmes(txt = msg))
        }
    }
}
