# GPSWayPointingApplication
Android-Kotlin
---------------------------------------------------------------------------------------------------------------------------------------------------------

CUSTOMVIEW 

I started my project by creating a custom view. 
So I created a class Compass View. 
I calculated the screen width  so that the compass is adapted to the screen size. 
After calculating the width and height values of the screen, 
I added the radius of compass to 0.7 times the width so that there is no overflow on the screen.

.drawCircle(cx , cy, radius, paint) : I assigned half of the width of cx and half of the cy of the height so that the compass is in the centre.

.drawLine(startx, starty, stopx, stopy, paint) : I used drawline for the compass needle. I assigned the width and height values of the screen for the coordinates

.rotate(degree, x, y) : I used this parameter to rotate the canvas around (x, y) point by the degree

I highlighted the four major compass directions (N, E, S, W) with north in a different colour to the other compass directions. So I used 

.drawText(text, x, y, paint) : I assigned the width and height values of the screen for the coordinates and I used a paint parameter for the north in red colour to the other compass directions black colour.

.restore() : I used this parameter to restore the state saved before Canvas.

.save() :  I used this parameter to save the state of Canvas.

---------------------------------------------------------------------------------------------------------------------------------------------------------

THE ROTATION VECTOR

I created the rotation vector to angle the compass to point in the direction the user is facing.

.getDefaultSensor(Sensor TYPE) : I wrote this code for  the sensor exists then I added the listener if it is there. I assigned the rotation vector for the sensor type.

. registerListener(SensorEventListener) : I added sensor listener

. getRotationMatrixFromVector(rotation_matrix) : I used get the rotation Matrix.

. getOrientation(rotation_matrix, orientation_values) : I used get the actual orientation of my device using getOrientation() 

.onSensorChanged() : I used for the gyroscope for use which measures the speed of rotation of the device in  Gravity directions.

I used this value for rotation by adding 360

---------------------------------------------------------------------------------------------------------------------------------------------------------

THE LOCAL LOCATION

.checkSelfPermission() : I used a this code for the permissions for the fine and coarse location

.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0f, object ) : I used this parameter for  update the user’s location. Time delay between updates: How much time in milliseconds the listener should wait until a new location is provided to the listener. I wrote 5000 translates to 5 seconds.

.requestPermission() : I requested the permissions for the location  I added a request code

.removeUpdates(locationListener) : This code to stop Listener of GPS location

---------------------------------------------------------------------------------------------------------------------------------------------------------

RECYCLERVIEW AND CARDVIEW

I created a Way Point data class these class primary constructor and the WaypointAdapter class for dynamic list with Recycler View. Once I've determined my layout, I need to implement Adapter and ViewHolder. These two classes work together to define how these data is displayed. The ViewHolder is a wrapper around a View that contains the layout for an individual item in the list. The Adapter creates ViewHolder objects as needed, and also sets the data for those views.

When you define my adapter, we need to override three key methods:

•	onCreateViewHolder(parent,viewtype): RecyclerView calls this method whenever it needs to create a new ViewHolder. The method creates and initializes the ViewHolder and its associated View, but does not fill in the view's contents—the ViewHolder has not yet been bound to specific data.

•	onBindViewHolder(holder,position): RecyclerView calls this method to associate a ViewHolder with data. The method fetches the appropriate data and uses the data to fill in the view holder's layout.  Each waypoint data in the holder item using position

•	getItemCount(): RecyclerView calls this method to get the size of the data set. RecyclerView uses this to determine when there are no more items that can be displayed.

----------------------------------------------------------------------------------------------------------------------------------------------------------
