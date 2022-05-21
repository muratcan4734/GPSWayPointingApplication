package com.murat.gps_assignment03

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.*
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var _sm : SensorManager
    private lateinit var _lm: LocationManager
    private lateinit var _rv: RecyclerView
    lateinit var compassView:CompassView

    private var _rotation_matrix:FloatArray = FloatArray(16){ 0f }
    private var _orientation_values:FloatArray = FloatArray(4){ 0f }
    private var angle:Int = 0

    private lateinit var _btnStart:Button
    private lateinit var _btnStop:Button
    private lateinit var _btnAdd:Button
    private lateinit var  _btnDelete:Button
    private lateinit var _linear_layout:LinearLayout

    private var currentLocation: Location? = null
    private var waypoint_number:Int = 0
    private var waypointName:String = ""
    private var mLatitude: Double = 0.0
    private var mLongitude: Double = 0.0
    private var geomagneticField: GeomagneticField? = null
    var waypointsList = ArrayList<WayPoint>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compassView = findViewById(R.id.compassView_Id)
        _btnStart = findViewById(R.id.BtnStart)
        _btnStop = findViewById(R.id.BtnStop)
        _btnAdd = findViewById(R.id.BtnAdd)
        _btnDelete = findViewById(R.id.BtnClean)
        _rv = findViewById(R.id.recyclerView_Waypoint_id)

        _btnStop.isEnabled = false
        _btnAdd.isEnabled = false
        _btnDelete.isEnabled = false

        _sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        _lm = getSystemService(LOCATION_SERVICE) as LocationManager


        addCopmass()

        _btnStart.setOnClickListener(){
            _btnStart.isEnabled = false
            _btnStop.isEnabled = true
            addLocationListener()
        }

        _btnStop.setOnClickListener(){
            _btnStop.isEnabled = false
            _btnStart.isEnabled = true
            _btnAdd.isEnabled = true
            onPause()
            Toast.makeText(this,"Stop Listener GPS Success",Toast.LENGTH_LONG).show()
        }
        _btnAdd.setOnClickListener(){
            _btnAdd.isEnabled = false
            _btnStop.isEnabled = false
            _btnStart.isEnabled = true
            _btnDelete.isEnabled = true
            if(waypoint_number == 0){
                waypointName = "WayPoint"+waypoint_number.toString()
                Toast.makeText(this@MainActivity,"Name: ${waypointName}\nLatidu: ${mLatitude}\nLongitude: ${mLongitude}",Toast.LENGTH_LONG).show()
                waypoint_number +=1
            }
            else{
                Toast.makeText(this@MainActivity,"ERROR There is a same WayPoint",Toast.LENGTH_SHORT).show()
            }
        }

        _btnDelete.setOnClickListener(){
            AlertDialog.Builder(this)
                .setTitle("View Point Deleted")
                .setMessage("Are you sure you want to delete View Point?")
                .setPositiveButton(
                    android.R.string.yes
                ) { dialog, _ ->
                    clearListLocation()
                }
                .setNegativeButton(android.R.string.no, null)
                .show()
        }
    }

    override fun onPause() {
        super.onPause()
        _lm.removeUpdates(locationListener)
    }

    private fun addLocationListener() {
        try {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION), 1)

                return
            }

            //the location updates the user’s location every 5 seconds
            _lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0f, object :
                LocationListener {
                override fun onLocationChanged(p0: Location) {
                    mLatitude = p0.latitude
                    mLongitude = p0.longitude
                }
            })

        }
        catch (e:Exception){
            Toast.makeText(this,"ERROR: $e",Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1) {

            if(grantResults[0] == PackageManager.PERMISSION_DENIED || grantResults[1] == PackageManager.PERMISSION_DENIED) {
                var snackbar: Snackbar = Snackbar.make(_linear_layout, "App will not work without location permissions", Snackbar.LENGTH_LONG)
                snackbar.show() } else {
                var snackbar: Snackbar = Snackbar.make(_linear_layout, "location permissions granted", Snackbar.LENGTH_LONG)
                snackbar.show() }
        } }

    private fun addCopmass(){
        if (_sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)!= null){
            _sm.registerListener(object :SensorEventListener{

                override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                }

                override fun onSensorChanged(p0: SensorEvent?) {
                    SensorManager.getRotationMatrixFromVector(_rotation_matrix,p0!!.values)
                    SensorManager.getOrientation(_rotation_matrix,_orientation_values)

                    _orientation_values[0] = Math.toDegrees(_orientation_values[0].toDouble()).toFloat()

                    if (_orientation_values[0] < 0){
                        compassView.rotationValue (_orientation_values[0]+360)
                        angle = (_orientation_values[0] + 360).toInt()
                    } else{
                        compassView.rotationValue(_orientation_values[0])
                        angle = (_orientation_values[0]).toInt()
                    }
                }
            },_sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),SensorManager.SENSOR_DELAY_UI)
        }
    }

    private fun clearListLocation(){
        waypointsList.clear()
        _btnStart.isEnabled = true
        _btnStop.isEnabled = false
        _btnAdd.isEnabled = false
        _btnDelete.isEnabled = false
        Toast.makeText(this,"All Way Point Delete Success",Toast.LENGTH_SHORT).show()
    }

    val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            currentLocation = location
            mLatitude = location.latitude
            mLongitude = location.longitude
            geomagneticField = GeomagneticField(
                currentLocation!!.latitude.toFloat(),
                currentLocation!!.longitude.toFloat(),
                currentLocation!!.altitude.toFloat(),
                System.currentTimeMillis()
            )
        }
    }

}