package helloandroid.ut3.com

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HelloAndroid : AppCompatActivity(), SensorEventListener {

    private lateinit var tv : TextView

    private lateinit var sm : SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tv = TextView(this)
        tv.text = "Salut"
        setContentView(tv)
        sm = getSystemService(SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        val mMagneticField = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        sm.registerListener(this, mMagneticField, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onStop() {
        val mMagneticField = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        sm.unregisterListener(this, mMagneticField)
        super.onStop()
    }

    override fun onSensorChanged(event: SensorEvent) {
        val sensor = event.sensor.type
        val values = event.values

        synchronized(this) {
            if (sensor == Sensor.TYPE_MAGNETIC_FIELD) {
                val magField_x = values[0]
                val magField_y = values[1]
                val magField_z = values[2]
                tv.text = "(x=$magField_x,y=$magField_y,z=$magField_z)"
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, i: Int) {
    }

}