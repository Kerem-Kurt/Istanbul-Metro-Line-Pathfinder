# Istanbul Metro Line Pathfinder
Example video from IstanbulHavalimani to SabihaGokcenHavalimani: https://youtu.be/Lu8yXoSkZ-Y 
<br /><br />

<h2>Explanation of the algorithm:</h2><h5><ol>
	<li> Iterated through each line of the coordinates.txt file to group the contents as lines, colors, and contentOfLines.</li>
	<li> Draw the lines, white circles, and then the names of the stations.</li>
	<li> Turned station names into numbers for easier analysis. Used the first index of appearance of breakpoints.</li>
	<li> Created an index bases coordinates list for numbers to match.</li>
	<li> Created a 2D ArrayList to store the neighbors of every number, also stored as numbers.</li>
	<li> Created 2 methods, each going through every neighbor. However, the first one always goes to the first neighbor while the second one always goes to the last neighbor.</li>
	<li> Removed the elements after the end station number in each list.</li>
	<li> Compare the common ones to find the right path.</li>
	<li> Turned numbers into coordinates via the list at 3.</li>
	<li> Iterated through every coordinate.</li>
	<li> Draw a big orange circle at the current one and a small orange circle for all the previous ones.</li>
</ol>	
</h5>
Example of the created map:
	
![withLines](https://github.com/Kerem-Kurt/Istanbul-Metro-Line-Pathfinder/assets/121832450/217dd149-0abf-459b-979a-dc71e4688e77)
