//Jamie Brindle (06352322)
//Group A
//Programming (PR1201)
//Assignment 2 - Applets

import java.awt.*;
import java.applet.*;
import java.net.*;
import java.awt.event.*;

public class brindlej_groupA_assign2 extends Applet implements MouseMotionListener,MouseListener,ActionListener
{    
    Font mainStage,SnickersStage,regular,box;                             //declared font variables
    
    Image offscreenImage;                                                 //declared offscreenImage for double buffering
    Image download_gif,key_gif;                                           //declared static images                                                                                      
    Image camping,beer,toilets,disabled,firstaid,food,information;        //declared 'symbol' images
    Image markets,merchandise,parking,shopping,showers,all;                   
    URL base;                                                             //declared URL
    TextField input;                                                      //declared textfield for 'searching''
    Button search;                                                        //declared button for 'submitting'
    Button clear;                                                         //declared button for 'clearning' search
    String word="null";                                                   //string declaration for textfield - input
    
//search arrays:    (used as means of searching by entering text into a text box, if the text matches up to
//                   a particular array a corrisponding action will be processed)
    String searchCamping[]={"camping", "tents,", "sleeping"};                          
    String searchBeer[]={"beer", "alcohol", "beverages", "booze"};
    String searchToilets[]={"toilets","lavatory", "loo", "bogs", "urinal"};
    String searchDisabled[]={"disabled", "wheelchair"};
    String searchFirstaid[]={"first aid", "accident", "medicine","health"};
    String searchFood[]={"food", "dinner", "eating", "lunch","snacks","munch","nosh","restaurants","stalls", "sizzler"};
    String searchInformation[]={"information", "find out", "help"};        
    String searchMarkets[]={"markets", "gifts","spend"};
    String searchMerchandise[]={"tshirts", "merchandise"};
    String searchParking[]={"parking", "car"};
    String searchShopping[]={"shopping", "buy"};
    String searchShowers[]={"clean", "showers", "wash","shave"};
    String searchAll[]={"show all", "everything"};
    
    Graphics og;                                                          //declared graphics name - background image for double buffering
       
    MediaTracker mt;                                                      //declare a media tracker to import and view images
    
    int mousex, mousey;                                                   //declared mouse x, y points for MouseListener & MouseMotionListener
    
    //declared x,y,width,height for mouseMoved (rollover) hotspots     
        int orangex,orangey,orangewidth,orangeheight;                     //orange camping zone                                 
        int redx,redy,redwidth,redheight;                                 //red camping zone
        int bluex,bluey,bluewidth,blueheight;                             //blue camping zone   
        int purplex,purpley,purplewidth,purpleheight;                     //purple camping zone
        int mainx,mainy,mainwidth,mainheight;                             //mainstage and the village zone
        int pressx,pressy,presswidth,pressheight;                         //press hospitality zone
        int sx,sy,swidth,sheight;                                         //snickers stage zone
        int sbx,sby,sbwidth,sbheight;                                     //snickers bowl zone
        int getx,gety,getwidth,getheight;                                 //get on it bowl zone
        int shockx,shocky,shockwidth,shockheight;                         //aftershock bowl zone
        int fairx,fairy,fairwidth,fairheight;                             //fair ground zone
        int allhx,allhy,allhwidth,allhheight;                             //further information box zone
        
    //declared x,y,width,height for mouseClicked (buttons) hotspots (on the key)
        int campingx,campingy,campingwidth,campingheight;                 //camping  
        int beerx,beery,beerwidth,beerheight;                             //beer
        int toiletsx,toiletsy,toiletswidth,toiletsheight;                 //toiles
        int disabledx,disabledy,disabledwidth,disabledheight;             //disabled
        int firstaidx,firstaidy,firstaidwidth,firstaidheight;             //first aid
        int foodx,foody,foodwidth,foodheight;                             //food
        int informationx,informationy,informationwidth,informationheight; //information
        int marketsx,marketsy,marketswidth,marketsheight;                 //markets
        int showersx,showersy,showerswidth,showersheight;                 //showers
        int merchandisex,merchandisey,merchandisewidth,merchandiseheight; //merchandise
        int parkingx,parkingy,parkingwidth,parkingheight;                 //parking
        int shoppingx,shoppingy,shoppingwidth,shoppingheight;             //shopping
        int allx,ally, allwidth, allheight;;                              //all
                
    //declared boolean names for all hotspots        
        boolean orangeZone,redZone,blueZone,purpleZone,mainZone,mouseEntered,pressZone,snickersZone;            //for site mouse rollovers
        boolean snickersBowl,getBowl,shockBowl,fairb,markscreen;
        boolean campingb,beerb,toiletsb,disabledb,firstaidb,foodb,informationb,marketsb,merchandiseb;           //for key mouse clicks
        boolean parkingb,shoppingb,showersb,allb,allhover;
        boolean campingbQ,beerbQ,toiletsbQ,disabledbQ,firstaidbQ,foodbQ,informationbQ,marketsbQ,merchandisebQ;  //for key mouse rollovers
        boolean parkingbQ,shoppingbQ,showersbQ,allbQ,allhoverQ;
        
    //polygon coordinates for those which have mouse rollovers:    
        int xOrangeCamping[]={280,435,488,478,420,390,315};                         //for orange camping zone
        int yOrangeCamping[]={160,65,135,160,200,160,190};
        
        int xRedCamping[]={500,580,620,661,580};                                    //for red campingm zone
        int yRedCamping[]={126,70,120,195,220};
        
        int xBlueCamping[]={620,660,780,783,661,};                                  //for blue camping zone
        int yBlueCamping[]={120,90,90,140,195};
        
        int xPurpleCamping[]={661,783,790,745};                                     //for purple camping zone
        int yPurpleCamping[]={195,140,260,312};
        
        int xMainArea[]={661,745,764,670,645,600,580,420,400,480,580};              //for main area zone
        int yMainArea[]={195,312,345,400,430,440,430,420,320,280,220};
                
    public void start()
    //this method will be call before anything else
    {
        addMouseListener(this);                                           //to allow rolovers and mouse dragging
        addMouseMotionListener(this);                                     //to allow mouse clicks & mouse entering and exit of applet
    }    
    
    public void init()
    //this method will be called before the 'paint' method to preset parameters and variables
    {
        setBackground(new Color(151,151,151));                          //background color of applet
        setSize(800,600);                                               //size of applet
        
        input = new TextField("Enter a searchword",15);                 //search text field initialised
        search = new Button("Search");                                  //search button initialised
        clear = new Button("Clear");                                    //clear button initialised
        setLayout(null);                                                //allows manipulation of buttons/text fields
        add(input);                                                     //input text field added to the applet
        add(search);                                                    //serach button added to applet         
        add(clear);                                                     //clear button added to applet
        input.setLocation(410,9);                                       //sets location of text field
        input.setSize(140,22);                                          //sets size of text field
        input.addActionListener(this);                                  //text field actively listening for input
        search.setLocation(562,10);                                     //sets location of search button
        search.setSize(60,22);                                          //sets size of serach button
        search.addActionListener(this);                                 //text field actively listening for input
        clear.setLocation(630,10);                                      //sets location of clear button                      
        clear.setSize(60,22);                                           //sets size of clear button
        clear.addActionListener(this);                                  //clear button actively listening for input
        
        
//Pre-set fonts
        mainStage = new Font("Tahoma",Font.BOLD,16);                    //font for 'main stage' and 'village' label
        SnickersStage = new Font("Arial Bold",Font.PLAIN,12);           //font for 'snickers stage' label
        box = new Font("Tahoma",Font.PLAIN,12);                         //font type for text in the 'further information' box
        regular = new Font("Arial",Font.PLAIN,12);                      //font type for text in the 'further information' box
                
        offscreenImage = createImage(size().width, size().height);      //offscreen image initialisation for double buffering
        og = offscreenImage.getGraphics();                              
        
        //initialisation of x,y,width,height for mouseMoved (rollover) hotspots       
            pressx = 350; pressy = 460; presswidth = 130; pressheight = 40;                         //press hospitality zone
            sx = 172; sy = 272; swidth = 116; sheight = 41;                                         //snickers stage zone
            sbx = 550; sby = 275; sbwidth = 35; sbheight = 35;                                      //snickers bowl zone
            getx = 575; gety = 320; getwidth = 32; getheight = 32;                                  //get on it bowl zone
            shockx = 590; shocky = 385; shockwidth = 50; shockheight = 30;                          //aftershock bowl zone                          
            allhx = 8; allhy = 520; allhwidth = 270; allhheight = 70;                               //extra information box zone
            fairx = 677; fairy = 320; fairwidth = 47; fairheight = 47;                               //fair ground zone
                                    
        //initialisation of x,y,width,height for mouseClicked (buttons) hotspots (on the key)   
            campingx = 5; campingy = 368; campingwidth = 135; campingheight = 25;                   //camping
            beerx =5 ; beery= 240; beerwidth = 135; beerheight = 25;                                //beer
            toiletsx = 5; toiletsy = 177; toiletswidth = 135; toiletsheight = 25;                   //toilets
            disabledx = 5; disabledy = 116; disabledwidth = 135; disabledheight = 25;               //disabled
            firstaidx = 5; firstaidy = 273; firstaidwidth = 135; firstaidheight = 25;               //first aid
            foodx = 5; foody=146; foodwidth = 135; foodheight = 25;                                 //food
            informationx = 5; informationy = 210; informationwidth = 135; informationheight = 25;   //information
            marketsx = 5; marketsy = 401; marketswidth = 135; marketsheight = 25;                   //markets
            merchandisex = 5; merchandisey = 85; merchandisewidth = 135; merchandiseheight = 25;    //merchandise
            parkingx = 5; parkingy = 469; parkingwidth = 135; parkingheight = 25;                   //parking
            shoppingx = 5; shoppingy = 304; shoppingwidth = 135; shoppingheight = 25;               //shopping
            showersx = 5; showersy = 337; showerswidth = 135; showersheight = 25;                   //showers
            allx =8; ally =520; allwidth=270; allheight = 70;                                       //all
            
         
        mt = new MediaTracker(this);                                              //initialsation of media tracer
        //Download-Logo:
                try {base = getDocumentBase();}                                   //find applet path
		catch (Exception e) {}
		download_gif = getImage(base,"download-logo.gif");                    //the image is loaded
		mt.addImage(download_gif,1);                                          //media tracer gives image an ID
		try {mt.waitForAll();}
		catch (InterruptedException  e) {}                                    //when applet is found then the image is loaded here
	//Key:
                try {base = getDocumentBase();}                                 
		catch (Exception e) {}	
		key_gif = getImage(base,"key.gif");                         
		mt.addImage(key_gif,2);                                     
		try {mt.waitForAll();}
		catch (InterruptedException  e) {}                          
	//all symbols:		                        
                try {base = getDocumentBase();}                                  
	
		catch (Exception e) {}
                all = getImage(base,"all.gif");                             
		mt.addImage(all,3);                                         
                try {mt.waitForAll();}
                catch (InterruptedException  e) {}                          
	//camping symbols:
                try {base = getDocumentBase();}                                   
		catch (Exception e) {}                                
		camping = getImage(base,"camping.gif");          
		mt.addImage(camping,4);                                
		try {mt.waitForAll();}
		catch (InterruptedException  e) {}                          
        //beer symbols:
                try {base = getDocumentBase();}                                  		
                 catch (Exception e) {}
		beer = getImage(base,"beer.gif");          
		mt.addImage(beer,5);		
		try {mt.waitForAll();}
		catch (InterruptedException  e) {}                         
	//toilets symbols:
                try {base = getDocumentBase();}                         
		catch (Exception e) {}
		toilets = getImage(base,"toilets.gif");          
		mt.addImage(toilets,6);                               
                try {mt.waitForAll();}
		catch (InterruptedException  e) {}                          
	//disabled symbols:
                try {base = getDocumentBase();}                                  
		catch (Exception e) {}
		disabled = getImage(base,"disabled.gif");         
		mt.addImage(disabled,7); 		
		try {mt.waitForAll();}
		catch (InterruptedException  e) {}                                                      
        //firstaido symbols:
                try {base = getDocumentBase();}                                  
		catch (Exception e) {}
		firstaid = getImage(base,"firstaid.gif");          
		mt.addImage(firstaid,8);                                
                try {mt.waitForAll();}
		catch (InterruptedException  e) {}                         
	//food symbols:
                try {base = getDocumentBase();}                              
                 catch (Exception e) {}		
		food = getImage(base,"food.gif");          
		mt.addImage(food,9);                                                             
		try {mt.waitForAll();}
		catch (InterruptedException  e) {}                          
        //information symbols:
                try {base = getDocumentBase();}                                  
		catch (Exception e) {}
                information = getImage(base,"information.gif");          
		mt.addImage(information,10);                              
		try {mt.waitForAll();}
		catch (InterruptedException  e) {}                          
        //markets symbols:
                try {base = getDocumentBase();}                                 
		catch (Exception e) {}
                markets = getImage(base,"markets.gif");          
		mt.addImage(markets,11);                               
                try {mt.waitForAll();}
		catch (InterruptedException  e) {}                         
        //merchandise symbols:
                try {base = getDocumentBase();}                                 
                catch (Exception e) {}		
		merchandise = getImage(base,"merchandise.gif");       
		mt.addImage(merchandise,12);                       
		try {mt.waitForAll();}
		catch (InterruptedException  e) {}                          
        //parking symbols:
                try {base = getDocumentBase();}                                
                catch (Exception e) {}
                parking = getImage(base,"parking.gif");        
		mt.addImage(parking,13);                               
                try {mt.waitForAll();}
		catch (InterruptedException  e) {}                        
        //shopping symbols:
                try {base = getDocumentBase();}                                
                catch (Exception e) {}		
		shopping = getImage(base,"shopping.gif");          
		mt.addImage(shopping,14);    
		try {mt.waitForAll();}
		catch (InterruptedException  e) {}                         
        //showers symbols:
                try {base = getDocumentBase();}                               
                catch (Exception e) {}
		showers = getImage(base,"showers.gif");          
		mt.addImage(showers,15);                                		
                try {mt.waitForAll();}
		catch (InterruptedException  e) {}                         
                }  
    
    public void paint(Graphics g)
    //paints everything onto the applet
    {     
        setSize(800,600);                                       //set size of applet                                    
        update(g);
    }    
    
    public void update(Graphics g)
    //this method is a 'group' of methods that are to be painted on the applet
    {
        showStatus("Welcome the Donnington Download Festival 2007 Interactive Site Map"); //this text will appear on the applets 'status' bar
        Background(og);                               //(coloured rectangle) used to 'hide' painted images that shouldn't be shown'
        Images(og);                                   //a group of static images, needed to be painted before 'SiteOutLine' method
        SiteOutline(og);                              //containing the general site map outline, including camping zones
        Objects(og);                                  //containing the stages, bowls and press hosipitality parts of the site                                 
        Path(og);                                     //adds the path which links the whole site together
        Images2(og);                                  //adds 'symbol images' to the site depending on if they're called or not'
        Search(og);                                   //same as above but calls depending on the result of the search text field input
        g.drawImage(offscreenImage, 0, 0, this);      //outputs to the screen what has been stored in the offscreen image for double buffering
     }
    
    public void Background(Graphics g)
    //(coloured rectangle) used to 'hide' painted images that shouldn't be shown'
   {
       og.setColor(new Color(151,151,151));             
       og.fillRect(0,0,800,600);                        
   }
    
    public void SiteOutline(Graphics g)
    //containing the general site map outline, including camping zones
    {
        og.setColor(new Color(204,255,100));                                            //draws extra information box (buttom left corning)
        og.fillRoundRect(8,520,270,70,20,20);
        og.setColor(new Color(100,100,100));
        og.fillRoundRect(12,524,262,62,20,20);
        if(allhover)                                                                    //changes box colour when mouse rollover
        {
        g.setColor(Color.red);        
        og.fillRoundRect(8,520,270,70,20,20);
        og.setColor(new Color(100,100,100));
        og.fillRoundRect(12,524,262,62,20,20);
        }
        //whenever a mouse isn't rolling over a hotspot the string below is to be displayed in the extras box
            if(!(orangeZone || redZone || blueZone || purpleZone || mainZone || pressZone || snickersZone
                || snickersBowl || shockBowl || getBowl || fairb))
            {
                og.setFont(box);
                og.setColor(Color.white);
                og.drawString("You can explore the map with the mouse ",20,543);
                og.drawString("Click a symbol to find something specific",20,558);
                og.drawString("Or click this box to view all details", 20, 573);
            }
        
        og.setColor(new Color(204,255,100));                                            //draws yellow land polygon of site
        int xLandOutline[]={120,160,180,245,315,280,435,488,580,620,660,780,790,
                    745,768,790,772,750,720,688,640,480,480,350,350,320}; 
        int yLandOutline[]={400,236,236,190,190,160,65,135,70,120,90,90,260,312,
                    355,380,450,440,400,420,480,470,500,500,480,480};
        og.fillPolygon(xLandOutline,yLandOutline,xLandOutline.length);
                
        og.setColor(new Color(255,153,0));                                              //draws orange camping zone polygone of site
        og.fillPolygon(xOrangeCamping,yOrangeCamping,xOrangeCamping.length);        
        if (orangeZone)                                                                 //lightens polygon colour when mouse rollover 
        {
            g.setColor(new Color(255,255,255,100));
            og.fillPolygon(xOrangeCamping,yOrangeCamping,xOrangeCamping.length);
            og.setFont(box);
            og.setColor(Color.white);
            og.drawString("'Orange' Camping Zone", 20, 558);            
        }        
        
        og.setColor(new Color(204,0,0));                                                //draws red camping zone polygone of site        
        og.fillPolygon(xRedCamping,yRedCamping,xRedCamping.length);
        if (redZone)                                                                    //lightens polygon colour when mouse rollover 
        {
            g.setColor(new Color(255,255,255,100));
            og.fillPolygon(xRedCamping,yRedCamping,xRedCamping.length);
            og.setFont(box);
            og.setColor(Color.white);
            og.drawString("'Red' Camping Zone", 20, 558);            
        } 
        
        og.setColor(new Color(153,204,255));                                            //draws blue camping zone polygone of site        
        og.fillPolygon(xBlueCamping,yBlueCamping,xBlueCamping.length);
        if (blueZone)                                                                   //lightens polygon colour when mouse rollover 
        {
            g.setColor(new Color(255,255,255,100));
            og.fillPolygon(xBlueCamping,yBlueCamping,xBlueCamping.length);
            og.setFont(box);
            og.setColor(Color.white);
            og.drawString("'Blue' Camping Zone", 20, 558);            
        }        
        
        og.setColor(new Color(204,0,153));                                              //draws purple camping zone polygone of site        
        og.fillPolygon(xPurpleCamping,yPurpleCamping,xPurpleCamping.length);
        if (purpleZone)                                                                 //lightens polygon colour when mouse rollover 
        {
            g.setColor(new Color(255,255,255,100));
            og.fillPolygon(xPurpleCamping,yPurpleCamping,xPurpleCamping.length);
            og.setFont(box);
            og.setColor(Color.white);
            og.drawString("'Purple' Camping Zone", 20, 558);            
        }        
        
        og.setColor(new Color(0,204,0));                                                //draws main area camping zone polygone of site        
        og.fillPolygon(xMainArea,yMainArea,xMainArea.length);
        if (mainZone)                                                                   //lightens polygon colour when mouse rollover 
        {
            g.setColor(new Color(255,255,255,100));
            og.fillPolygon(xMainArea,yMainArea,xMainArea.length);
            og.setFont(box);
            og.setColor(Color.white);                                                   //draws string to extras box when mouse rollover
            og.drawString("This is the main attraction area with a crowd", 20, 543);
            og.drawString("70,000 people, world famous bands, lots of", 20, 558);
            og.drawString("rides and other entertainment in 'The Village'", 20, 573);
        }        
    }
    
    public void Objects(Graphics g)
    //containing the stages, bowls and press hosipitality parts of the site
   {
        og.setColor(new Color(204,204,204));                                            //draws snickers stage
        og.fillRect(170,270,120,45);
        og.setColor(Color.black);
        og.setFont(SnickersStage);
        og.drawString("Snickers Stage",187,298);
        og.drawOval(172,272,116,41);
        if (snickersZone)                                                               //draws string to extras box when mouse rollover
        {
            og.setFont(box);
            og.setColor(Color.white);
            og.drawString("If you don't fancy the line-up on the main",20,543);
            og.drawString("stage then get youself down to the Snickers",20,558);
            og.drawString("Stage for more famous live bands", 20, 573);
            og.setColor(Color.red);                                                    //and changers snickers stage oval colour when mouse rollover
            og.drawOval(172,272,116,41);
        }
        
        og.setFont(box);                                                               //draws press and hospitality building  
        og.setColor(new Color(125,125,125));                                           //and press and hospitality car park area
        og.fillRect(300,480,124,50);                
        og.setColor(new Color(204,204,204));                                                                                             
        og.fillRect(350,460,130,40);
        og.setColor(Color.black);
        og.drawString("Press Hosipitality", 371, 486);                                 
        og.drawRect(353,464,124,34);
        if (pressZone)                                                                 //draws string to extras box when mouse rollover                                                 
        {
            og.setFont(box);
            og.setColor(Color.white);
            og.drawString("Keep your eyes open because there will be", 20, 543); 
            og.drawString("lots of press and star guests around", 20, 558);
            og.setColor(Color.red);                                                    //and changes colour of press building inner rectangle
            og.drawRect(353,464,124,34);
        }
        
        og.setFont(regular);                                                     //draws snicker, geton it and aftershock bowls
        og.setColor(new Color(51,51,153));                                                  
        og.fillOval(550,275,35,35);                                                      //snicker bowl oval                                                
        og.fillOval(575,320,32,32);                                                      //get on it bowl oval                                                                                                        
        og.fillRoundRect(590,385,50,30,29,49);                                           //aftershock round rectangle       
        og.setColor(Color.white);
        og.drawString("S",564,297);                                                      //snickers bowl label
        g.drawString("G", 587, 341);                                                     //get on it bowl label
        g.drawString("A", 612, 405);                                                     //aftershock bowl label
        if(snickersBowl)                                                         //lightens colour of bowl when mouse rollover
        {
            og.setColor(new Color(255,255,255,100));
            og.fillOval(550,275,35,35);
            og.setColor(Color.yellow);                                           //and changes label colour when rollover
            og.drawString("S",564,297);
            og.setColor(Color.white);                                            //and draws the string to extras box when mouse rollover
            og.drawString("'Snickers Bowl'",20,558);
        }
        if(getBowl)                                                              //lightens colour of bowl when mouse rollover
        {
            og.setColor(new Color(255,255,255,100));
            og.fillOval(575,320,32,32);
            og.setColor(Color.yellow);                                           //and changes label colour when rollover
            og.drawString("G",587,341);
            og.setColor(Color.white);                                            //and draws the string to extras box when mouse rollover
            og.drawString("'Get On It Bowl'",20,558);
        }
        if(shockBowl)                                                            //lightens colour of bowl when mouse rollover
        {
            og.setColor(new Color(255,255,255,100));
            og.fillRoundRect(590,385,50,30,29,49); 
            og.setColor(Color.yellow);                                           //and changes label colour when rollover
            og.drawString("A",612,405); 
            og.setColor(Color.white);                                            //and draws the string to extras box when mouse rollover
            og.drawString("'AfterShock Bowl'",20,558);                                  
        }
        
        og.setFont(box);                                               //draws fair ground oval
        og.fillOval(677,320,47,47);                                    
        og.setColor(Color.black);                           
        og.drawString("Fair",692,340);                                 //draws labels for fair ground oval
        og.drawString("Ground",681,355);                                
        og.drawString("Main Entrance",700,425);                        //draws label for main entrance
        if (fairb)                                                     //puts a red ring around the fair ground when mouse rollover
        {
            og.setColor(Color.red);
            og.drawOval(677,320,47,47);                                //and changes the text to red when mouse rollover
            og.drawString("Fair",692,340);                                 
            og.drawString("Ground",681,355);
            og.setColor(Color.white);                                  //and displays the string in extras when rollover
            og.drawString("Fair gound, consisting of lots of scary rides,",20,543);  
            og.drawString("the famouse Passage de Terror, prizes and",20,558);
            og.drawString("masses of other entertainment", 20, 573);
        }
        
        MainStage(og);                                //the mainstage construction, in it's own method due to it complexity
        Scale(og);                                    //containing the 'scale' of the site in the button right corning                                 
        allTrees(og);                                 //add all trees to the site
   }
        
   public void MainStage(Graphics g)
   //the mainstage construction, in it's own method due to it complexity
   {
       og.setColor(new Color(153,153,153));                     //draws draws stage rectangle
       int xRect1[]={403,414,370,358};
       int yRect1[]={340,395,405,350};
       og.fillPolygon(xRect1,yRect1,xRect1.length);
       og.setColor(Color.black);                                //draw outline of stage rectangle
       og.drawPolygon(xRect1,yRect1,xRect1.length);
       
       int xTri1[]={403,414,395};                               //draws triangle or roof on rectangle
       int yTri1[]={340,395,370};
       og.drawPolygon(xTri1,yTri1,xTri1.length);
       og.drawLine(395,370,364,377);
       
       int xRect2[]={406,411,427,421};                          //draws top speakerss rectangle
       int yRect2[]={330,328,405,407};
       og.fillPolygon(xRect2,yRect2,xRect2.length);
       
       int xRect3[]={396,398,364,362};                          //draws buttom speakers rectangle
       int yRect3[]={332,341,349,340};
       og.fillPolygon(xRect3,yRect3,xRect3.length);
       
       int xRect4[]={410,412,378,375};                          //draws right speakers rectangle
       int yRect4[]={396,406,414,404};
       og.fillPolygon(xRect4,yRect4,xRect4.length);
       
       og.setColor(Color.white);                                //draws 'main stage' label string
       og.setFont(mainStage);
       og.drawString("Main Stage",440,370);
       
       og.drawString("The",640,280);                            //draws 'the village' label string
       og.drawString("Village",640,300);                
   }
   
    public void Scale(Graphics g)
    //containing the 'scale' of the site in the button right corning 
    {
        og.setColor(Color.white);                               //draws series of rectangles making up the scale in buttom right corner
        og.setFont(regular);
        og.fillRect(590,570,4,10);
        og.fillRect(594,573,192,4);
        og.fillRect(786,570,4,10);
        og.setColor(Color.white);
        og.drawString("500m", 675, 590);
    }
    
   public void Path(Graphics g)
   //adds the path which links the whole site together
   {    
       og.setColor(Color.white);                                //draws white walk path polygon part 1
       int xPath1[]={579,665,640,340,150,180,280,370,510,585,671,641,339,145,175,281,371,511,362,367,515,580,585};
       int yPath1[]={260,420,455,455,378,250,220,290,290,260,420,460,460,381,247,215,285,285,110,107,282,256,260};
       og.fillPolygon(xPath1,yPath1,xPath1.length);
       
       int xPath2[]={578,579,651,783,783,651,585,584};          //draws white walk path polygon part 2
       int yPath2[]={262,220,195,140,146,200,223,264};
       og.fillPolygon(xPath2,yPath2,xPath2.length);
       
       og.setColor(new Color(125,125,125));                     //draws grey road path polygon part 1
       int xPath3[]={768,710,540,540,530,530,703,760};
       int yPath3[]={448,519,560,600,600,554,513,444};
       og.fillPolygon(xPath3, yPath3, xPath3.length);
       
       og.fillRect(370,530,10,70);                              //draws grey road path rectangle part 2       
   }
   
   public void tree(Graphics g, int x, int y)
   //method that draws a tree and allows the tree to be replicated anywhere on the applet using only the x and y coordinates
   {
       og.setColor(Color.green);                               //green tree part (polygon)
       int xTree[]={0+x,16+x,8+x};
       int yTree[]={25+y,25+y,0+y};
       og.fillPolygon(xTree,yTree,xTree.length);
       og.setColor(new Color(153,102,0));                      //brown tree part or trunk (rectangle)
       og.fillRect(4+x,25+y,8,4);        
   }
   public void allTrees(Graphics g)
   //draws all trees to the site by refering to 'tree' method and adding x, y coordinates
   {
       tree(og,240,230);
       tree(og,260,235);
       tree(og,280,230);
       
       tree(og,220,330);
       tree(og,180,340);
       tree(og,240,345);
       tree(og,260,350);
       tree(og,280,330);
       tree(og,200,355);
       
       tree(og,320,200);
       tree(og,340,180);
       tree(og,350,220);
       tree(og,365,190);
       tree(og,380,220);
       tree(og,395,200);
       tree(og,470,175);
       tree(og,490,160);
       tree(og,510,180);
   }  
   
   public void Images(Graphics g)
   //a group of static images, needed to be painted before 'SiteOutLine' method
   {
       og.drawImage(download_gif, 10, 10, this);                    //download logo
       og.drawImage(key_gif,0,75,this);                             //key (left hand site containing all key symbols)
   }
   
   public void Images2(Graphics g)
   //draws 'symbol images' to the site depending on if they're called or not'
   {
       og.setColor(Color.white);
       if(campingb) og.drawImage(camping,0,0,this);                 //camping symbol
       if(beerb) og.drawImage(beer,0,0,this);                       //beer symbol
       if(toiletsb) og.drawImage(toilets,0,0,this);                 //toilets symbol
       if(disabledb) og.drawImage(disabled,0,0,this);               //disabled symbol 
       if(firstaidb) og.drawImage(firstaid,0,0,this);               //first aid symbol
       if(foodb) og.drawImage(food,0,0,this);                       //food symbol
       if(informationb) og.drawImage(information,0,0,this);         //information symbol
       if(marketsb) og.drawImage(markets,0,0,this);                 //markets symbol
       if(merchandiseb) og.drawImage(merchandise,0,0,this);         //merchandise symbol
       if(parkingb) og.drawImage(parking,0,0,this);                 //parking symbol
       if(shoppingb) og.drawImage(shopping,0,0,this);               //shopping symbol
       if(showersb) og.drawImage(showers,0,0,this);                 //showers symbol
       if(allb) og.drawImage(all,0,0,this);                         //all symbols
       
       keyRollovers(og);                                            //marks a circle next to a symbol when a mouse rolls over it
   }
   
   public void keyRollovers(Graphics g)
   //marks a circle next to a symbol when a mouse rolls over it
   {
       og.setColor(Color.red);
       if(campingbQ) og.fillOval(campingx,campingy,8,8);              //camping symbol
       if(beerbQ) og.fillOval(beerx,beery,8,8);                       //beer symbol
       if(toiletsbQ) og.fillOval(toiletsx,toiletsy,8,8);              //toilets symbol
       if(disabledbQ) og.fillOval(disabledx,disabledy,8,8);           //disabled symbol 
       if(firstaidbQ) og.fillOval(firstaidx,firstaidy,8,8);           //first aid symbol
       if(foodbQ) og.fillOval(foodx,foody,8,8);                       //food symbol
       if(informationbQ) og.fillOval(informationx,informationy,8,8);  //information symbol
       if(marketsbQ) og.fillOval(marketsx,marketsy,8,8);              //markets symbol
       if(merchandisebQ) og.fillOval(merchandisex,merchandisey,8,8);  //merchandise symbol
       if(parkingbQ) og.fillOval(parkingx,parkingy,8,8);              //parking symbol
       if(shoppingbQ) og.fillOval(shoppingx,shoppingy,8,8);           //shopping symbol
       if(showersbQ) og.fillOval(showersx,showersy,8,8);              //showers symbol
   }
   
   public void mouseMoved(MouseEvent e)
   //this method tracks mouse movement and each statement gives the specific boolean a value depending on where the 
   //mouse is at the time and the appearance of the site (mainly rollovers) depend on the value of these booleans
    {
       mousex = e.getX();                   //gets the x position of the mouse and saves it in this variable
       mousey = e.getY();                   //gets the y position of the mouse and saves it in this variable
       
       //rollovers for site:
            if(new Polygon(xOrangeCamping,yOrangeCamping,xOrangeCamping.length).contains(mousex,mousey)) //for orange camp zone rollover
            orangeZone = true;
            else orangeZone = false;
       
            if(new Polygon(xRedCamping,yRedCamping,xRedCamping.length).contains(mousex,mousey))         //for red camp zone rollover
            redZone = true;
            else redZone = false;
       
            if(new Polygon(xBlueCamping,yBlueCamping,xBlueCamping.length).contains(mousex,mousey))      //for blue camp zone rollover
            blueZone = true;
            else blueZone = false;
       
            if(new Polygon(xPurpleCamping,yPurpleCamping,xPurpleCamping.length).contains(mousex,mousey))//for purple camp zone rollover
            purpleZone = true;
            else purpleZone = false;
       
            if(new Polygon(xMainArea,yMainArea,xMainArea.length).contains(mousex,mousey))               //for main area zone rollover
            mainZone = true;
            else mainZone = false;
                       
            if (mousex > pressx && mousex < pressx + presswidth &&                            //for press hospitaly building mouse rollover
                   mousey > pressy && mousey < pressy+pressheight) pressZone = true;
            else pressZone = false;
        
            if (mousex > sx && mousex < sx + swidth &&                                        //for snickers stage mouse rollover
                    mousey > sy && mousey < sy+sheight) snickersZone = true;
            else snickersZone = false;
        
            if (mousex > sbx && mousex < sbx + sbwidth &&                                     //for snickers bowl mouse rollover          
                 mousey > sby && mousey < sby+sbheight) snickersBowl = true;
            else snickersBowl = false;
        
            if (mousex > getx && mousex < getx + getwidth &&                                  //for get on it bowl mouse rollover
                    mousey > gety && mousey < gety+getheight) getBowl = true;
            else getBowl = false;
        
            if (mousex > shockx && mousex < shockx + shockwidth &&                            //for aftershock bowl mouse rollover
                    mousey > shocky && mousey < shocky+shockheight) shockBowl = true;
            else shockBowl = false;
        
            if (mousex > allhx && mousex < allhx + allhwidth &&                               //for extra information box mouse rollover
                    mousey > allhy && mousey < allhy + allhheight) allhover = true;
            else allhover = false;
            
            if (mousex > fairx && mousex < fairx + fairwidth &&                               //for fair ground mouse rollover
                    mousey > fairy && mousey < fairy + fairheight) fairb = true;
            else fairb = false;
                    
            if(snickersBowl || getBowl || fairb || shockBowl) mainZone = false;              //if snickers bowl or get on it bowl or fair ground or
                                                                                        //aftershock bowl is rolled over, the main zone unhighlights
       //key rollovers:
            if (mousex > campingx && mousex < campingx + campingwidth &&                              //for camping symbol mouse rollover
                    mousey > campingy && mousey < campingy+campingheight) campingbQ = true;
            else campingbQ = false;
        
            if (mousex > beerx && mousex < beerx + beerwidth &&                                       //for beer symbol mouse rollover
                    mousey > beery && mousey < beery+beerheight) beerbQ = true;
            else beerbQ = false;
        
            if (mousex > toiletsx && mousex < toiletsx + toiletswidth &&                              //for toilets symbol mouse rollover
                    mousey > toiletsy && mousey < toiletsy+toiletsheight) toiletsbQ = true;
            else toiletsbQ = false;
        
            if (mousex > disabledx && mousex < disabledx + disabledwidth &&                           //for disabled symbol mouse rollover
                    mousey > disabledy && mousey < disabledy + disabledheight) disabledbQ = true;
            else disabledbQ = false;
        
            if (mousex > firstaidx && mousex < firstaidx + firstaidwidth &&                           //for first aid symbol mouse rollover
                    mousey > firstaidy && mousey < firstaidy + firstaidheight) firstaidbQ = true;
            else firstaidbQ = false;
        
            if (mousex > foodx && mousex < foodx+foodwidth &&                                         //for food symbol mouse rollover
                    mousey > foody && mousey < foody+foodheight) foodbQ = true;
            else foodbQ = false;
        
            if (mousex > informationx && informationx < informationx+informationwidth &&             //for information symbol mouse rollover
                    mousey > informationy && mousey < informationy+informationheight) informationbQ = true;
            else informationbQ = false;
        
            if (mousex > marketsx && mousex < marketsx + marketswidth &&                             //for markets symbol mouse rollover
                    mousey > marketsy && mousey < marketsy + marketsheight) marketsbQ = true;
            else marketsbQ = false;
        
            if (mousex > merchandisex && mousex < merchandisex + merchandisewidth &&                 //for merchandise symbol mouse rollover
                    mousey > merchandisey && mousey < merchandisey + merchandiseheight) merchandisebQ = true;
            else merchandisebQ = false;
        
            if (mousex > parkingx && mousex < parkingx+parkingwidth &&                               //for parking symbol mouse rollover
                    mousey > parkingy && mousey < parkingy+parkingheight) parkingbQ = true;
            else parkingbQ = false;
        
            if (mousex > shoppingx && mousex < shoppingx + shoppingwidth &&                           //for shopping symbol mouse rollover
                    mousey > shoppingy && mousey < shoppingy + shoppingheight) shoppingbQ = true;
            else shoppingbQ = false;
        
            if (mousex > showersx && mousex < showersx + showerswidth &&                              //for showers symbol mouse rollover
                 mousey > showersy && mousey < showersy + showersheight) showersbQ = true;
            else showersbQ = false;
       
        repaint();                      //repaints 'paint' method as the boolean values change due to mouse movement
    }
   
    public void mouseDragged(MouseEvent e){}
    public void mousePressed (MouseEvent e){}   
    public void mouseReleased (MouseEvent e) {}   
    public void mouseEntered (MouseEvent e){}    
    public void mouseExited (MouseEvent e){}    
    public void mouseClicked (MouseEvent e)
    //this methods listens to mouse clicks and each statement give the specific boolean a value depending on whether an area
    //on the applet is clicked or not. the appearance of the site (mainly key symbols) depend on the value of these booleans
    {
        mousex = e.getX();                   //gets the x position of the mouse and saves it in this variable
        mousey = e.getY();                   //gets the y position of the mouse and saves it in this variable
        	        
        if (mousex > campingx && mousex < campingx + campingwidth &&                              //for camping symbol mouse click
                mousey > campingy && mousey < campingy+campingheight) campingb = true;
        else campingb = false;
        
        if (mousex > beerx && mousex < beerx + beerwidth &&                                       //for beer symbol mouse click
                mousey > beery && mousey < beery+beerheight) beerb = true;
        else beerb = false;
        
        if (mousex > toiletsx && mousex < toiletsx + toiletswidth &&                              //for toilets symbol mouse click
                mousey > toiletsy && mousey < toiletsy+toiletsheight) toiletsb = true;
        else toiletsb = false;
        
        if (mousex > disabledx && mousex < disabledx + disabledwidth &&                           //for disabled symbol mouse click
                mousey > disabledy && mousey < disabledy + disabledheight) disabledb = true;
        else disabledb = false;
        
        if (mousex > firstaidx && mousex < firstaidx + firstaidwidth &&                           //for first aid symbol mouse click
                mousey > firstaidy && mousey < firstaidy + firstaidheight) firstaidb = true;
        else firstaidb = false;
        
        if (mousex > foodx && mousex < foodx+foodwidth &&                                         //for food symbol mouse click
                mousey > foody && mousey < foody+foodheight) foodb = true;
        else foodb = false;
        
        if (mousex > informationx && mousex < informationx+informationwidth &&              //for information symbol mouse click
                mousey > informationy && mousey < informationy+informationheight) informationb = true;
        else informationb = false;
        
        if (mousex > marketsx && mousex < marketsx + marketswidth &&                              //for markets symbol mouse click
                mousey > marketsy && mousey < marketsy + marketsheight) marketsb = true;
        else marketsb = false;
        
        if (mousex > merchandisex && mousex < merchandisex + merchandisewidth &&                  //for merchandise symbol mouse click
                mousey > merchandisey && mousey < merchandisey + merchandiseheight) merchandiseb = true;
        else merchandiseb = false;
        
        if (mousex > parkingx && mousex < parkingx+parkingwidth &&                                //for parking symbol mouse click
                mousey > parkingy && mousey < parkingy+parkingheight) parkingb = true;
        else parkingb = false;
        
        if (mousex > shoppingx && mousex < shoppingx + shoppingwidth &&                           //for shopping symbol mouse click
                mousey > shoppingy && mousey < shoppingy + shoppingheight) shoppingb = true;
        else shoppingb = false;
        
        if (mousex > showersx && mousex < showersx + showerswidth &&                              //for showers symbol mouse click
                mousey > showersy && mousey < showersy + showersheight) showersb = true;
        else showersb = false;
        
        if (mousex > allx && mousex < allx + allwidth &&                                          //for extra information box mouse click
                mousey > ally && mousey < ally + allheight) allb = true;
        else allb = false;
                   
        repaint();                      //repaints 'paint' method as the boolean values change due to mouse clicks
    }
    
    public void actionPerformed(ActionEvent e)
    //this methods used for text fields and buttons that allow the user to search particular things on the site and
    //allows the user to clear the previous search
       {
        word = e.getActionCommand();                //sets the previously declared string 'word' to that of the users input       
                if (e.getSource() == search)        //if 'search' button pressed, string 'word' set to that of the users input
		{
			word = input.getText();		
		}
                if (e.getSource() == clear)         //sets strins 'word' to "null" so that it clears the previous search    
                {                                   //and turns all key booleans to false to clear the site of symbols
                        word = "null";
                        campingb = false; beerb = false; toiletsb = false; disabledb = false; firstaidb = false; 
                        foodb = false; informationb = false; marketsb = false;  merchandiseb = false; parkingb = false; 
                        shoppingb = false;  showersb = false;  allb = false; allhover = false; 
                        input.setText("Enter a searchword");        //put 'enter a searchword back' into the text field
                }
		repaint();                          //repaints applet due to changes made in 'word' string
    }
    
    public void Search(Graphics g)
    //this methods searches through each 'search' array to find a possible match with the users input to the text field
    {
        for(int i = 0; i < searchCamping.length; i++)          //iterates variable 'i' as many times as the size of the array
            {
                if(searchCamping[i].toLowerCase().indexOf(word.toLowerCase()) > -1) //checks to see if the inputted text matches any part  
                {                                                                   //of the string in the array (array value is value of 'i')
                    og.drawImage(camping,0,0,this);            //if a match exists, a particular images is drawn to sreen, in this case
                }                                              //the image is 'camping' which shows all camping symbols on the site
            }        
        for(int i = 0; i < searchBeer.length; i++)                          //for beer symbols
            {
                if(searchBeer[i].toLowerCase().indexOf(word.toLowerCase()) > -1)
                {
                    og.drawImage(beer,0,0,this); 
                }            
            }        
        for(int i = 0; i < searchToilets.length; i++)                       //for toilets symbols
            {
                if(searchToilets[i].toLowerCase().indexOf(word.toLowerCase()) > -1)
                {
                    og.drawImage(toilets,0,0,this); 
                }
            }
        for(int i = 0; i < searchDisabled.length; i++)                      //for disabled symbols
            {
                if(searchDisabled[i].toLowerCase().indexOf(word.toLowerCase()) > -1)
                {
                    og.drawImage(disabled,0,0,this); 
                }
            }      
        for(int i = 0; i < searchFirstaid.length; i++)                      //for first aid symbols
            {
                if(searchFirstaid[i].toLowerCase().indexOf(word.toLowerCase()) > -1)
                {
                    og.drawImage(firstaid,0,0,this); 
                }
            }
        for(int i = 0; i < searchFood.length; i++)                          //for food symbols
            {
                if(searchFood[i].toLowerCase().indexOf(word.toLowerCase()) > -1)
                {
                    og.drawImage(food,0,0,this); 
                }
            }
        for(int i = 0; i < searchInformation.length; i++)                   //for information symbols
            {
                if(searchInformation[i].toLowerCase().indexOf(word.toLowerCase()) > -1)
                {
                    og.drawImage(information,0,0,this); 
                }
            }
        for(int i = 0; i < searchMarkets.length; i++)                       //for market symbols
            {
                if(searchMarkets[i].toLowerCase().indexOf(word.toLowerCase()) > -1)
                {
                    og.drawImage(markets,0,0,this); 
                }
            }
        for(int i = 0; i < searchParking.length; i++)                       //for parking symbols
            {
                if(searchParking[i].toLowerCase().indexOf(word.toLowerCase()) > -1)
                {
                    og.drawImage(parking,0,0,this); 
                }
            }
        for(int i = 0; i < searchShopping.length; i++)                      //for shopping symbols
            {
                if(searchShopping[i].toLowerCase().indexOf(word.toLowerCase()) > -1)
                {
                    og.drawImage(shopping,0,0,this); 
                }
            }
        for(int i = 0; i < searchShowers.length; i++)                       //for shower symbols
            {
                if(searchShowers[i].toLowerCase().indexOf(word.toLowerCase()) > -1)
                {
                    og.drawImage(showers,0,0,this); 
                }
            }
        for(int i = 0; i < searchAll.length; i++)                           //for all symbols
            {
                if(searchAll[i].toLowerCase().indexOf(word.toLowerCase()) > -1)
                {
                    og.drawImage(all,0,0,this); 
                }
            }
        for(int i = 0; i < searchMerchandise.length; i++)                   //for merchandise symbols
            {
                if(searchMerchandise[i].toLowerCase().indexOf(word.toLowerCase()) > -1)
                {
                    og.drawImage(merchandise,0,0,this); 
                }
            }
     }
}
