# html-scrapper
A Utility to capture each words as well as pair of words in the document parsed from a URL.
It uses Jsoup library to get the contents of the URL in the form of a document which is then parsed to collect all the words inside it. This similarly processes further URLs present in the document in a level order traversal manner. This only goes on till the maximum depth is reached which is set on start-up of the application (default is 1).   
Note: It makes takes longer time to process if the Url contains too many URLs inside it or you increase the max depth.
<h3>Prerequisite</h3>
<ul>
    <li>Java 8</li>
    <li>Maven</li>
    <li>Active Internet</li>
</ul>

<h3>Process to Run</h3>
After building, you can run this project in two ways:
<ul>
    <li>`java -jar target/html-scrapper-1.0-SNAPSHOT.jar`</li>
    <li>`java -cp target/html-scrapper-1.0-SNAPSHOT.jar com.scrapper.html.HtmlScrapper`</li>
</ul>

<h3>Implementation</h3>
<ul>
<li>Processing input
    <ul>
        <li>Input for the URL to process</li>
        <li>Input for the maximum depth needed</li>
        <li>Input for minimum valid word length</li>
    </ul>
</li>
<li>It puts the given URL in queue and starts processing.</li>
<li>During processing, it parses the URL document and maintains internal Trie to keep track of the valid words/word pairs encountered.</li>
<li>For every URL, it gets all the links present in the document and keep them further in the queue while increasing the level.</li>
<li>Once the queue is empty or, a maximum level is reached - the process gets terminated.</li>
<li>Upon termination, it prints the top 10 frequent words/word pairs encounters along with their count.</li>
</ul>


 