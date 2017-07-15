package system.design;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
/*
Design a simple yelp system. Support the following features:
Add a restaurant with name and location.
Remove a restaurant with id.
Search the nearby restaurants by given location.
A location is represented by latitude and longitude, both in double. A Location class is given in code.
Nearby is defined by distance smaller than k Km .
Restaurant class is already provided and you can directly call Restaurant.create() to create a new object. 
Also, a Helper class is provided to calculate the distance between two location, use Helper.get_distance(location1, location2).
A GeoHash class is provided to convert a location to a string. Try GeoHash.encode(location) to convert location to a geohash string and 
GeoHash.decode(string) to convert a string to location.
 Notice
Learn about GeoHash http://www.lintcode.com/en/help/geohash/
addRestauraunt("Lint Cafe", 10.4999999, 11.599999) // return 1
addRestauraunt("Code Cafe", 10.4999999, 11.512109) // return 2
neighbors(10.5, 11.6, 6.7) // return ["Lint Cafe"]
removeRestauraunt(1) 
neighbors(10.5, 9.6, 6.7) // return []
// The distance between location(10.5, 11.6) and "Lint Code" is 0.0001099 km
// The distance between location(10.5, 11.6) and "Code Code" is 9.6120978 km
*/
// Definition of Location:
public class MiniYelp {
    public NavigableMap<String, Restaurant> restaurants; // GeoHash+ID, Restaurant
    public Map<Integer, String> ids; // <RestaurantID, GeoHash+ID> 同一个地点可能有好多饭店
    
    public MiniYelp() {
        ids = new HashMap<Integer, String>();
        restaurants = new TreeMap<String, Restaurant>();
    }

    // @param name a string
    // @param location a Location
    public int addRestaurant(String name, Location location) {
        Restaurant restaurant = Restaurant.create(name, location);
        String hashcode = GeoHash.encode(location) + "." + restaurant.id;
        ids.put(restaurant.id, hashcode);
        restaurants.put(hashcode, restaurant);
        return restaurant.id;
    }

    // @param restaurant_id an integer
    public void removeRestaurant(int restaurant_id) {
        if (ids.containsKey(restaurant_id)) {
            String hashcode = ids.get(restaurant_id);
            ids.remove(restaurant_id);
            if (restaurants.containsKey(hashcode)) {
                restaurants.remove(hashcode);
            }
        }
    }

    // @param location a Location
    // @param k an integer, distance smaller than k miles
    // @return a list of restaurant's name and sort by 
    // distance from near to far.
    public List<String> neighbors(Location location, double k) {
        // Write your code here
        int len = get_length(k);
        String hashcode = GeoHash.encode(location);
        hashcode = hashcode.substring(0, len);

        List<Node> res = new ArrayList<Node>();
        NavigableMap<String, Restaurant> subMap = restaurants.subMap(hashcode, true, hashcode + "{", true); // In ASCII, { == z + 1
        
		for (Map.Entry<String, Restaurant> entry : 
                subMap.entrySet()) {
            String key = entry.getKey();
            Restaurant value = entry.getValue();
            double distance = Helper.get_distance(location, value.location);
            if (distance <= k)
                res.add(new Node(distance, value));
        }
		
        Collections.sort(res, new Comparator<Node>(){  
            public int compare(Node arg0, Node arg1) {  
                if (arg0.distance < arg1.distance)
                    return -1;
                else if (arg0.distance > arg1.distance)
                    return 1;
                else
                    return 0;
            }  
        });
        
        List<String> rt = new ArrayList<String>();
        int n = res.size();
        for (int i = 0; i < n; ++i)
            rt.add(res.get(i).restaurant.name);
        
        return rt;
    }

    // geoHash这个string的长度决定了精度，这个方法是根据要搜索的范围，大体估算出geoHash需要的长度来
    int get_length(double k) {
        double[] ERROR = {2500, 630, 78, 20, 2.4, 0.61, 0.076, 0.01911, 0.00478, 0.0005971, 0.0001492, 0.0000186};
        for (int i = 0; i < 12; ++i)
            if (k  > ERROR[i])
                return i;
        return 12;
    }
    
    static class Location {
        public double latitude, longitude;
        public static Location create(double lati, double longi) {
            // This will create a new location object
            return null;
        }
    };

    // Definition of Restaurant
    static class Restaurant {
        public int id;
        public String name;
        public Location location;
        public static Restaurant create(String name, Location location) {
            // This will create a new restaurant object,
            // and auto fill id
            return null;
        }
    };

    // Definition of Helper
    static class Helper {
        public static double get_distance(Location location1, Location location2) {
            // return distance between location1 and location2.
            return 0.0;
        }
    };

    static class GeoHash {
        public static String encode(Location location) {
            // return convert location to a GeoHash string
            return "";
        }
        public static Location decode(String hashcode) {
             // return convert a GeoHash string to location
             return null;
        }
    };

    class Node {
        public double distance;
        public Restaurant restaurant;
        public Node(double d, Restaurant r) {
            distance = d;
            restaurant = r;
        }
    }
}