# Istanbul Metro Line Pathfinder
Example video from IstanbulHavalimani to SabihaGokcenHavalimani: https://youtu.be/Lu8yXoSkZ-Y 

Explanation of the algorithm: 
1-	Iterated through each line of the coordinates.txt file to group the contents as lines, colors, and contentOfLines.
2-	Draw the lines, white circles, and then the names of the stations.
3-	Turned station names into numbers for easier analysis. Used the first index of appearance of breakpoints.
4-	Created an index bases coordinates list for numbers to match.
5-	Created a 2D ArrayList to store the neighbors of every number, also stored as numbers.
6-	Created 2 methods, each going through every neighbor. However, the first one always goes to the first neighbor while the second one always goes to the last neighbor.
7-	Removed the elements after the end station number in each list.
8-	Compare the common ones to find the right path.
9-	Turned numbers into coordinates via the list at 3. 
10-	 Iterated through every coordinate.
11-	Draw a big orange circle at the current one and a small orange circle for all the previous ones.


Example of the created map:
![withLines](https://github.com/Kerem-Kurt/Istanbul-Metro-Line-Pathfinder/assets/121832450/ca705a11-2678-4298-9ba1-98b46ffe8595)
