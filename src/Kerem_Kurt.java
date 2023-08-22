/*
   Name : Kerem Haktan Kurt
   Date : 26.03.2023
   Code Summary: Matched stations with their neighbours via using numbers as indexes,
                 Used two recursive method. First one looking for the FIRST neighbours of every neighbour
                 while keeping in mind the visited one via visitedList, after finding the end station, removed
                 later elements of the search. Second one looking for the LAST neighbours of every neighbour
                 while keeping in mind the visited one via secondVisitedList, then doing the same thing as before.
                 Finally, compared them and only used the common ones for the correct path.
                 Then after each big orange circle drawing we keep draving all the former small orange ones.
  */

import javax.swing.plaf.synth.SynthUI;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Kerem_Kurt {
    public static void main(String[] args) throws FileNotFoundException {

        // Filename and creating file object to open
        String fileName = "input.txt";
        File file = new File(fileName);

        // Creating Arrays which metro lines will fit
        // First array for lines
        // Second array for remembering colors and matching them via indexing with the lines
        // Third array is for deeply analyzing all the stations of lines
        // 4th is for matching stations with lines
        // 5th is for matching coordinates with lines || use indexing to match with stations
        String[] metroLines = new String[10];
        String[] colorOfLines = new String[10];
        String[] contentOfLines = new String[10];
        ArrayList<ArrayList<String>> linesWithStations = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> linesStationCoords = new ArrayList<ArrayList<String>>();

        // Read the file and part the contents
        Scanner inputFile = new Scanner(file);
        int i = 0;
        String line = inputFile.next();
        while (inputFile.hasNextLine()) {
            if (i % 3 == 0) {
                if (i != 0) {
                    line = inputFile.next();
                }
                metroLines[i / 3] = line;
                line = inputFile.next();
                colorOfLines[i / 3] = line;
            } else {
                line = inputFile.nextLine();
                contentOfLines[i / 3] = line;
            }
            i = i + 1;
            if (i == 30) {
                break;
            }
        }
        i = 0;
        inputFile.close();

        Scanner reader = new Scanner(System.in);
        String startPos = reader.next();
        String endPos = reader.next();

        // Creating the canvas
        int canvasWidth = 1024;
        int canvasHeight = 482;
        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(0, 1024);
        StdDraw.setYscale(0, 482);
        StdDraw.picture(512, 241, "background.jpg");
        StdDraw.enableDoubleBuffering();


        // Drawing the lines and stations on the canvas
        for (int a = 0; a < 10; a++) {
            int lengthOfContent = contentOfLines[a].split(",").length;

            Scanner scan = new Scanner(contentOfLines[a]);
            int[] drawingCoord = new int[4];

            // Loop that draws the lines
            for (int m = 0; m < lengthOfContent * 2 - 2; m++) {
                String contentLines = scan.next();
                String[] lineCoord = contentLines.split(",");

                if (m == 1) {
                    drawingCoord[2] = Integer.parseInt(lineCoord[0]);
                    drawingCoord[3] = Integer.parseInt(lineCoord[1]);
                }
                if (m % 2 == 1) {
                    drawingCoord[0] = Integer.parseInt(lineCoord[0]);
                    int tempX = Integer.parseInt(lineCoord[0]);

                    drawingCoord[1] = Integer.parseInt(lineCoord[1]);
                    int tempY = Integer.parseInt(lineCoord[1]);

                    // Drawing the lines
                    StdDraw.setPenRadius(0.012);
                    String[] rgbColors = colorOfLines[a].split(",");
                    StdDraw.setPenColor(Integer.parseInt(rgbColors[0]), Integer.parseInt(rgbColors[1]), Integer.parseInt(rgbColors[2]));
                    StdDraw.line(tempX, tempY, drawingCoord[2], drawingCoord[3]);
                    drawingCoord[2] = Integer.parseInt(lineCoord[0]);
                    drawingCoord[3] = Integer.parseInt(lineCoord[1]);
                }
            }

            // Loop that draws the circles
            Scanner scanDot = new Scanner(contentOfLines[a]);
            String tempStationName;
            boolean nameChecker = false;
            tempStationName = "None";
            ArrayList<String> tempLines = new ArrayList<String>();
            ArrayList<String> tempCoords = new ArrayList<String>();
            for (int n = 0; n < lengthOfContent * 2 - 2; n++) {
                String contentDots = scanDot.next();

                // Writing the Station Names on the canvas
                if (n % 2 == 0) {
                    if (contentDots.startsWith("*")) {
                        tempLines.add(contentDots.substring(1));
                    } else {
                        tempLines.add(contentDots);
                    }
                    if (contentDots.startsWith("*")) {
                        nameChecker = true;
                        tempStationName = contentDots.substring(1, contentDots.length());
                    }
                }

                if (n % 2 == 1) {
                    String[] lineCoord = contentDots.split(",");
                    drawingCoord[0] = Integer.parseInt(lineCoord[0]);
                    drawingCoord[1] = Integer.parseInt(lineCoord[1]);

                    tempCoords.add(contentDots);

                    // Drawing the circles
                    StdDraw.setPenRadius(0.01);
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.line(drawingCoord[0], drawingCoord[1], drawingCoord[0], drawingCoord[1]);

                    // Checking which stations to write
                    if (nameChecker) {
                        StdDraw.setPenColor(StdDraw.BLACK);
                        StdDraw.setFont(new Font("Helvetica", Font.BOLD, 8));
                        StdDraw.text(drawingCoord[0], drawingCoord[1] + 5, tempStationName);
                        nameChecker = false;
                    }
                }
            }
            linesWithStations.add(tempLines);
            linesStationCoords.add(tempCoords);
            tempCoords = new ArrayList<String>();
            tempLines = new ArrayList<String>();
        }

        ArrayList<ArrayList<Integer>> stationNeighboursNumbered = new ArrayList<ArrayList<Integer>>();
        ArrayList<String> stationsNumbered = new ArrayList<String>();

        // Numbering Stations for easier analyzing
        for (ArrayList<String> station : linesWithStations) {
            for (String s : station) {
                if (!stationsNumbered.contains(s)) {
                    stationsNumbered.add(s);
                }
            }
        }

        // Matching the numbered stations with their coordinates
        ArrayList<String> stationsCoordinated = new ArrayList<String>();
        for (int j = 0; j < linesStationCoords.size(); j++) {
            for (int m = 0; m < linesStationCoords.get(j).size(); m++) {
                if (!stationsCoordinated.contains(linesStationCoords.get(j).get(m))) {
                    stationsCoordinated.add(linesStationCoords.get(j).get(m));
                }
            }
        }

        // Creating a 2d Arraylist for storing neighbours of stations
        for (ArrayList<String> withStation : linesWithStations) {
            for (int j = 0; j < withStation.size(); j++) {
                stationNeighboursNumbered.add(new ArrayList<Integer>());
            }
        }

        // Finding the neighbours of stations ( Using numbering format)
        for (ArrayList<String> linesWithStation : linesWithStations) {
            for (int j = 0; j < linesWithStation.size(); j++) {
                if (j == linesWithStation.size() - 1) {
                    stationNeighboursNumbered.get(stationsNumbered.indexOf(linesWithStation.get(j))).add(stationsNumbered.indexOf(linesWithStation.get(j - 1)));
                } else if (j == 0) {
                    stationNeighboursNumbered.get(stationsNumbered.indexOf(linesWithStation.get(j))).add(stationsNumbered.indexOf(linesWithStation.get(j + 1)));
                } else {
                    stationNeighboursNumbered.get(stationsNumbered.indexOf(linesWithStation.get(j))).add(stationsNumbered.indexOf(linesWithStation.get(j + 1)));
                    stationNeighboursNumbered.get(stationsNumbered.indexOf(linesWithStation.get(j))).add(stationsNumbered.indexOf(linesWithStation.get(j - 1)));
                }
            }
        }


        boolean[] visitedList = new boolean[stationsNumbered.size()];
        boolean[] secondVisitedList = new boolean[stationsNumbered.size()];
        for (boolean a : visitedList) {
            a = false;
        }
        for (boolean a : secondVisitedList) {
            a = false;
        }

        int startNum = stationsNumbered.indexOf(startPos);
        int endNum = stationsNumbered.indexOf(endPos);


        if (startNum == -1 || endNum == -1) {
            System.out.println("The station names provided are not present in this map.");
            System.exit(0);
        }

        // Using dfs to find the way it reached the destination (via always using the FİRST neighbours)
        ArrayList<Integer> emptyRoute = new ArrayList<Integer>();
        ArrayList<Integer> tempRoute = pathFinder(stationNeighboursNumbered, visitedList, startNum, endNum, emptyRoute);

        // Using dfs to find the way it reached the destination (via always using the LAST neighbours)
        ArrayList<Integer> secondEmptyRoute = new ArrayList<Integer>();
        ArrayList<Integer> secondTempRoute = reversePathFinder(stationNeighboursNumbered, secondVisitedList, startNum, endNum, secondEmptyRoute);


        // If end number found, no needed the rest
        ArrayList<Integer> necessaryRoute = new ArrayList<Integer>();
        for (int num : tempRoute) {
            necessaryRoute.add(num);
            if (num == endNum) {
                break;
            }
        }
        ArrayList<Integer> secondNecessaryRoute = new ArrayList<Integer>();
        for (int num : secondTempRoute) {
            secondNecessaryRoute.add(num);
            if (num == endNum) {
                break;
            }
        }

        // Finding the common ones for correct path
        ArrayList<Integer> finalRoute = new ArrayList<Integer>();
        for (int firstNum : necessaryRoute) {
            if (secondNecessaryRoute.contains(firstNum)) {
                finalRoute.add(firstNum);
            }
        }

        // Coordinating the final route
        ArrayList<String> routeCoordinates = new ArrayList<String>();
        for (int a : finalRoute) {
            routeCoordinates.add(stationsCoordinated.get(a));
        }

        if (finalRoute.get(finalRoute.size() - 1) != endNum) {
            System.out.println("These two stations are not connected");
            System.exit(0);
        }

        for (int k : finalRoute) {
            System.out.println(stationsNumbered.get(k));
        }

        // FINAL DRAWING
        StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
        StdDraw.show();
        StdDraw.save("withLines.png");

        int pauseDuration = 300;
        for (int j = 0; j < routeCoordinates.size(); j++) {
            String[] coords = routeCoordinates.get(j).split(",");
            int xCoord = Integer.parseInt(coords[0]);
            int yCoord = Integer.parseInt(coords[1]);

            StdDraw.clear();
            StdDraw.picture(512, 241, "withLines.png", 1024, 482);

            // Drawing the previous small orange circles
            for (int k = 0; k < j; k++) {
                String[] smallCoords = routeCoordinates.get(k).split(",");
                int smallXCoord = Integer.parseInt(smallCoords[0]);
                int smallYCoord = Integer.parseInt(smallCoords[1]);
                StdDraw.setPenRadius(0.01);
                StdDraw.line(smallXCoord, smallYCoord, smallXCoord, smallYCoord);
                StdDraw.show();
            }
            StdDraw.setPenRadius(0.02);
            StdDraw.line(xCoord, yCoord, xCoord, yCoord);
            StdDraw.show();
            StdDraw.pause(pauseDuration);

        }
    }


    private static ArrayList<Integer> pathFinder(ArrayList<ArrayList<Integer>> adjLists, boolean[] visited, int v, int endNum, ArrayList<Integer> route) {
        visited[v] = true;
        route.add(v);
        if (v == endNum) {
            return route;
        }
        for (int w : adjLists.get(v)) {
            if (!visited[w]) {
                pathFinder(adjLists, visited, w, endNum, route);
            }
        }
        return route;
    }

    private static ArrayList<Integer> reversePathFinder(ArrayList<ArrayList<Integer>> adjLists, boolean[] visited, int v, int endNum, ArrayList<Integer> route) {
        visited[v] = true;
        route.add(v);
        if (v == endNum) {
            return route;
        }
        for (int j = adjLists.get(v).size() - 1; j > -1; j--) {
            int w = adjLists.get(v).get(j);
            if (!visited[w]) {
                reversePathFinder(adjLists, visited, w, endNum, route);
            }
        }
        return route;
    }
}