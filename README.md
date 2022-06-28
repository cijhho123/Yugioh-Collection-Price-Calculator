[![banner](/recources/assets/banner.png)](https://yugiohprices.com/)

# Yugioh Collection Price Calculator
A tool to help people evaluate their Yugioh collection's prices with up do date information with the YugiohPrices API 

## Technical Information
The program read all the files and folders recurcievly from the path given by the user. </br>
For each .txt file in the given path the program will read all the print tags, a unique ID for each version of each card from the Yugioh TCG.</br>
Each print tag will be sent to the YugiohPrices API as a GET request and it will return all the relevant information.</br>
After the program will finish gathering all the information it will create a text file called "Output" at the same path provided by the user. the output file will contain the collection's price in 3 different methods:</br>
- The <b>lowest price</b> for the collection based on the lowest priced eBay listing. </br>
- The <b>average price</b> for the collectoon based on employing a recursive algorithm that uses standard deviations to filter out outlier card prices in order to produce a more statistically sound average price. </br>
- The <b>highest price</b> for the collection based on sales from recet time. </br>

For more information about the pricing methods feel free to visit:</br>
https://yugiohprices.com/about </br>
https://blog.yugiohprices.com/post/90183367316/lowest-card-price-is-now-picked-using-ebay </br>
https://blog.yugiohprices.com/post/54460976914/how-are-average-prices-calculated </br>

## How To Use

### How to uniquely identify a Yugioh card?
A Card Number, not to be confused with the eight-digit code at the lower-left corner of cards) is the ID code found on most OCG and TCG cards, intended to denote the set from which the card originated, and its position in that set. Another purpose for card numbers is to help players distinguish a card's Original Print from its Reprints. </br>
Card numbers are located immediately underneath the image on most cards, on the right-hand side.

for more information visit: </br>
https://yugioh.fandom.com/wiki/Card_Number

### Important information
The program will try to send each row of each text file to the YugiohPrices server as a print tag. therefore, please make sure that the only text files in the path you provide the program contains only print tags, in order to prevert accidentally flooding the server with useless requests.

The program need text files in specific format. </br> 
Each print tag in a new line, without spaces before or after the tag. for example text file click [here](/recources/assets/example.txt).

### Get the right path
In order to get the wanted folder's path:
1. go to the folder's location
2. right click on the icon > click <b>"Properties"</b>
3. find the <b>"Location"</b> field and copy its entire content </br>
 (make sure you copy the entire thing, if the path is too long it won't show everything and you'll have to scroll to the side)
4. run the program and paste the path and you are told to.

### Choose the version you want: Normal version Vs. Efficient version
The normal Version print each file discoverd and each GET request sent to the YugiohPrices server, which can make scanning really large collections needlesly slow.</br>
whereas the efficient version remove all those printing and display only the welcome menu, end menu and errors if any shows up.</br>
the option to choose a version will be inside the program.</br>

### Running the program
#### The program need to run from CMD (Windows Command Prompt). </br>
STEP 1: Download the version you want. [Normal version](/assets/YugiohPriceCalc_normal) or [Efficient version](/recources/jar/YugiohPriceCalc_efficient)</br>
STEP 2: Open <b>CMD</b> and navigate to the folder containing the jar file</br>
STEP 3: In the CMD write: 	for the Normal version:		<b>java -jar YugiohPriceCalc_normal.jar</b></br>
							for the Efficient version:	<b>java -jar YugiohPriceCalc_efficient.jar</b></br>
STEP 4: Follow the instructions from inside the program.


## Disclaimer
- The program pulles the data from the YugiohPrices API which updates every few hours.
- You use this program at your own risk. I (the programer) and YugiohPrices team won't hold responsibility for any loss of money or goods caused by using this program.
- Currently the program only support print tag for the english version of cards, any card in different language will be found in the text files will be ignored and its value won't be added to the total value of the collection! this limitation is on YugiohPrices' end and i will work on a solution in the future.
 

## System Requirements
- Up to date Java
- 500 KB of free space
- Stable internet connection

## License
This project is licensed under the GNU General Public License v3.0 - see the [LICENSE.md](LICENSE) file for details. </br>
> Permissions of this strong copyleft license are conditioned on making available complete source code of licensed works and modifications, which include larger works using a licensed work, under the same license. Copyright and license notices must be preserved. Contributors provide an express grant of patent rights.

## Credits
- All the coding was done by me, for any feedback or complains please open an issue or contact me directly.

- Thanks to the YugiohPrices team for their wonderful price tracking abillities and free API. </br>
at https://yugiohprices.com

- Thanks to patorjk website for their ascii art generator </br>
at https://patorjk.com/software/taag/ 
