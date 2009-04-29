select * from LOCATION where
 (street is not null OR city is not null OR state is not null OR zip is not null) AND
 (latitude is null OR longitude is null);