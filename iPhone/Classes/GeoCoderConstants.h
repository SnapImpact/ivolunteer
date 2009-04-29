//
//  GeoCoderConstants.h
//  geocoder
//
//  Created by Hassan Abdel-Rahman on 12/20/08.
//  Copyright 2008 __MyCompanyName__. All rights reserved.
//

#define LATITUDE			 @"LAT"
#define LONGITUDE			 @"LONG"
#define STREET_NUMBER		 @"STREET_NUMBER"
#define ADDRESS				 @"ADDRESS"
#define STREET				 @"STREET"
#define CITY				 @"CITY"
#define STATE				 @"STATE"
#define ZIP					 @"ZIP"
#define COUNTY				 @"COUNTY"
#define COUNTRY				 @"COUNTRY"
#define PRECISION			 @"PRECISION"
#define GOOGLE_GEOCODER_URL		      @"http://maps.google.com/maps/geo?q=%@&output=csv&sensor=true&key=ABQIAAAAxf5J-_jVgKRbDpskg3t42RSR01KzBBpIhKv2bUDH74RSKxGyxhRCT5Qntm61OY-qLqOYNQXyLeTwRQ"
#define ZONETAG_REVERSE_GEOCODER_URL  @"http://zonetag.research.yahooapis.com/services/rest/V1/suggestedTags.php?apptoken=b969ed7c9c446a39adb09ca8a3d84f97&latitude=%lf&longitude=%lf&output=xml"
#define GEONAMES_REVERSE_GEOCODER_URL @"http://ws.geonames.org/findNearestAddress?lat=%lf&lng=%lf&radius=1"
#define YAHOO_STRICT_GEOCODER_URL     @"http://local.yahooapis.com/MapsService/V1/geocode?appid=3Gff3uLV34EHMJIPQUwUA40NFyK1ZwRIqoFIJIZbF1P9AT8EfxAncWZ6EJPzX3XB123GlsM-&street=%@&city=%@&state=%@&zip=%@"
#define YAHOO_RELAXED_GEOCODER_URL    @"http://local.yahooapis.com/MapsService/V1/geocode?appid=3Gff3uLV34EHMJIPQUwUA40NFyK1ZwRIqoFIJIZbF1P9AT8EfxAncWZ6EJPzX3XB123GlsM-&location=%@"