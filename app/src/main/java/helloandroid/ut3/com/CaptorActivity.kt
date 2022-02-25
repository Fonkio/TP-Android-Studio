package helloandroid.ut3.com

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class CaptorActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var tvValueX : TextView
    private lateinit var tvValueY : TextView
    private lateinit var tvValueZ : TextView

    private lateinit var sm : SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_captor)
        tvValueX = findViewById(R.id.xValue)
        tvValueY = findViewById(R.id.yValue)
        tvValueZ = findViewById(R.id.zValue)
        sm = getSystemService(SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        if (sm.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).size != 0) {
            val mMagneticField = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
            sm.registerListener(this, mMagneticField, SensorManager.SENSOR_DELAY_NORMAL)
        }
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
                val (magFieldX, magFieldY, magFieldZ) = values
                tvValueX.text = "$magFieldX"
                tvValueY.text = "$magFieldY"
                tvValueZ.text = "$magFieldZ"
                Log.d("x", "$magFieldX")
                Log.d("y", "$magFieldY")
                Log.d("z", "$magFieldZ")
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, i: Int) {
    }

    fun finishActivity(view : View) {
        finish()
    }

}