package com.mycompany.app;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public final class BikeSharingUsagePatterns {

    public static void main(String[] args) throws Exception {

        JavaSparkContext sc = new JavaSparkContext(new SparkConf().setMaster("local").setAppName("BikesharingUsagePatterns").set("spark.files.overwrite", "true"));

        JavaRDD<String> data = sc.textFile("trip.csv");
        int i = 0;
        JavaRDD<Trip> rdd_trips = data.filter(line -> {
            String[] fields = line.split(",");
                        return (fields.length == 11);
                }).map(line -> {
            String[] fields = line.split(",");
            return new Trip(Integer.parseInt(fields[0]), Float.parseFloat(fields[1]), fields[2].trim(), fields[3], Integer.parseInt(fields[4]), fields[5], fields[6], Integer.parseInt(fields[7]), Integer.parseInt(fields[8]), fields[9], fields[10]);
        });

        // Part 3: calculate bike rides on a particular day
        JavaPairRDD<Trip, Integer> trips_today =  rdd_trips.filter(line -> {
            return line==line; //.getStart_date().contains("8/29/2013");
        }).mapToPair(line -> {
            return new Tuple2<>(line, 1);
        });

        JavaPairRDD<Integer, Integer> bikeRidesPerStationId = trips_today.mapToPair(s -> {
            return new Tuple2<>(s._1.getStart_station_id(), s._2);
        }).groupByKey().mapToPair(entry ->
                {
                    int total = 0;
                    for (Integer count : entry._2) {
                        total += count;
                    }
                    return new Tuple2<>(entry._1, total);
                }
        ).mapToPair(s -> {
            return new Tuple2<>(s._2, s._1);
        }).sortByKey();

        bikeRidesPerStationId.saveAsTextFile("outfile");
    }
}