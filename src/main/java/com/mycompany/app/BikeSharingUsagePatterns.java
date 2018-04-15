package com.mycompany.app;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class BikeSharingUsagePatterns {

    public static void main(String[] args) throws Exception {
        //predict_bike_usage_per_city();

        JavaSparkContext sc = new JavaSparkContext(new SparkConf().setMaster("local").setAppName("BikesharingUsagePatterns").set("spark.files.overwrite", "true"));

        JavaRDD<String> data = sc.textFile("status.csv");
        int i = 0;
        JavaRDD<Station> rdd_status = data.filter(line -> {
            String[] fields = line.split(",");
            return (fields.length == 4);
        }).map(line -> {
            String[] fields = line.split(",");
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = formatter.parse(fields[3]);
                String newDateString = formatter.format(new Date(date.getYear(), date.getMonth(), date.getDate(), date.getHours(), 0, 0));
                return new Station(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), Integer.parseInt(fields[2]), newDateString);

            }
            catch(Exception e)
            {
                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = formatter2.parse(fields[3]);
                String newDateString = formatter2.format(new Date(date.getYear(), date.getMonth(), date.getDate(), date.getHours(), 0, 0));
                return new Station(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), Integer.parseInt(fields[2]), newDateString);
            }
        });

        JavaRDD<String> mapped_time = rdd_status.mapToPair(station -> {
           return new Tuple2<>(station.getDate_time() + station.getStation_id(), station);
        }).groupByKey().map(tuple -> {
            int bikes = 0;
            int docks = 0;
            int station_id = 0;
            int count = 0;
            String date_time = null;
            for (Station station : tuple._2()){
                station_id = station.getStation_id();
                bikes += station.getBikes_available();
                docks += station.getDocks_available();
                date_time = station.getDate_time();
                count++;
            }
            Station final_station =  new Station(station_id, bikes / count, docks/ count, date_time);
            return final_station.toString();
        });

        mapped_time.saveAsTextFile("outfile");

        sc.textFile("outfile/part*").coalesce(1).saveAsTextFile("outfile/test.csv");

    }

    public static void predict_bike_usage_per_city() {
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