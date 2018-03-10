# bike-usage-patterns

### Processing based on cities

Count the number of bikes starting their rides on a particular day grouped by cities. Advise the bike sharing company to reduce bikes in some cities if demand is less.
Part 2: Find similarity between cities based on usage.


#### Psuedo code
Mapping phase:
1. Map all trips to a value "1" where time == time t. Output: (key, value) pairs like (Trip1, 1); (Trip2,1) etc.
2. Map all the cities of the respective trips: (Startstation1, 1), (StartStation2, 1) etc.

Reduce phase: g
Group by cities and add the counts to get the (StartStationId, number_of_trips_in_a_day).

Finally take the Station dataset and map the station Ids to cities and group based on that.
