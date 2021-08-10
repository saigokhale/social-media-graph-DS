import java.util.* ;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;


class a{  // ArrayList to store usernames
    public static ArrayList<String> userarr= new ArrayList<String>();
}

class account{
    String username, city, pass, dob, gender;
    long contact;
    static int count=0;
    int index;
    LinkedList<account> requests;
    LinkedList<account> following;
    LinkedList<account> followers;

    account() {
        followers = new LinkedList<account>();
        following = new LinkedList<account>();
        requests = new LinkedList<account>();
        username="";
        city="";
        pass="";
        dob="";
        gender= "";
        contact=0;
        count++;
        index=count;
    }
    
    public static boolean validateJavaDate(String strDate){
    	// Check if date is 'null' 
    	if (strDate.trim().equals("")){
    	    return true;
    	}
    	// Date is not 'null' 
    	else{
    	    SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/mm/yyyy");
    	    sdfrmt.setLenient(false);
    	    try{
    	        Date javaDate = sdfrmt.parse(strDate); //convert string into date format
    	    } 
    	    catch (ParseException e){
    	        System.out.println(strDate+" is Invalid Date format");
    	        return false;
    	    }
    	    return true;
    	}
    }
    
    void create_acc(){
        Scanner sc = new Scanner(System.in);
		System.out.print("\n<<< Enter account details >>>\n");
		
		//username
		retryun:
		while(true){
		    a aobj =new a();
    		System.out.print("Username: ");
    		username = sc.nextLine();
    		if(username.contains(" ")|| a.userarr.contains(username)){
    		    System.out.println("[ Invalid username. Enter username again.]");
    		    continue retryun;
    		}
    		else{
    		    a.userarr.add(username);
    		    break;
    		}
		}
		
		//password
		retrypass:
    		while(true){
        		System.out.print("Password: ");
        		pass = sc.next();
        		if(pass.length()<8) {
        		    System.out.println("[ Password must contain at least 8 characters.");
        		    System.out.println("  Enter password again ]");
        		    continue retrypass;
        		}
        		else
        		    break;
    		}
		
		//gender
		System.out.print("Gender: ");
		gender = sc.next();
		
		//dob
		retrydob:
		while(true){
    		System.out.print("Date of birth (dd/mm/yyyy): ");
    		dob = sc.next();
    		if(!validateJavaDate(dob)){
    		    System.out.println("[ Date of birth should be in dd/mm/yyyy format ]");
    		    continue retrydob;
    		}
    		else
    		    break;
		}
		//city
		System.out.print("City: ");
	    city = sc.next();
	    
	    //contact
	    retrycon:
	    while(true){
    		System.out.print("Contact number: " );
    		contact = sc.nextLong();
    		String cnt= new String();
    		cnt= Long.toString(contact);
    		if(cnt.length()!=10){
    		    System.out.println("[ Invalid contact number.");
    		    System.out.println("  Enter contact number again. ]");
    		    continue retrycon;
    		}
    		else
    		    break;
	    }
		System.out.println("ACCOUNT CREATED!");
		display_acc();
    }
     
    public void display_acc(){
        System.out.println("\n---------------Account details--------------------");
        System.out.println("Username: " + username);
        System.out.println("Gender: " + gender);
        System.out.println("Date of birth: " + dob);
        System.out.println("City: " + city);
        System.out.println("Contact Number: " + contact); 
        //System.out.println("Vertex index: " + index);  
        System.out.println("Followers: " + (followers.size()));
        System.out.println("Following: " + following.size());
        System.out.println("\n----------------------------------------------------" );
    }
}

/*
1.  void add_account(account a)
2.  void connect(account a, account b)
3.  void show_requests(account b)
4.  void accept_request(account b, account a)
5.  void disconnect(account a, account b)
6.  account get_account(String un)
7.  boolean username_exists(String un)
8.  account search_byUsername()
9.  void display_connections()  (following list)
10. void show_all()
11. void show_followers(account a)
12. void show_mutuals(account a, account b)
13. void get_recommendations(account a)
14. void bfsList(account a)

*/
class Graph{
	int n; 
	int e; 
	public static account admin= new account();  //connected to all existing nodes
	account acc[]=new account[100];
	
	Graph(){
		n = 1; // admin node exists
		e = 0;
	}
	
	// Add account to graph
	void add_account(account a){            
        acc[a.index]=a;
        n++;
        this.connect(admin,a);
    }
	
	// Connect accounts
	void connect(account a, account b){
        //System.out.println("Entered g.connect()"+a.username+","+b.username);
        if(a==b){
            System.out.println(a.username+": Invalid follow (cannot follow yourself!)");
        }
        else if(a.following.contains(b)){
            System.out.println(a.username+" already following "+ b.username);
        }
        else if(a==admin){
            admin.following.add(b);
        }
        else{
            if(b.requests.contains(a)){
                System.out.println(a.username+" already sent a request to "+ b.username);
            }
            else{
                b.requests.add(a);
                System.out.print(a.username+" sent a request to "+b.username);
            }
        }
    }
    
    // Show requests and ask to accept requests
    void show_requests(account b){
        Scanner sc = new Scanner(System.in);
        char ch='y';
        account a= new account();
        do{
        if(b.requests.size()<1){  // b.requestLL empty
            System.out.print(" [ "+b.username+" ] ");
            System.out.print("No pending requests \n");
            break;
        }
        else{
                System.out.println("\n-----------------REQUESTS-------------------------");
                System.out.println("   [ "+b.username+" ] pending requests: ");
                
                for(int i=0;i<(b.requests.size());i++){
                    System.out.print("["+(i+1)+". "+b.requests.get(i).username+"]  ");
                }
                System.out.println("\n----------------------------------------------------" );
                if((b.requests.size())<1){
                     System.out.println("\n----------------------------------------------------" );
                     System.out.print("               No more pending requests ");
                     System.out.println("\n----------------------------------------------------" );
                     break;
                }
                //System.out.println(b.requests.size());
                System.out.print("Enter the index of profile to accept request: ");
                int n= sc.nextInt();
                n--;
                a=b.requests.get(n);
                accept_request(b,a);
                System.out.print("Do you want to accept more requests?(y/n): ");
                ch= sc.next().charAt(0);
            }
        }while((ch=='y'||ch=='Y')); 
    }
    
    // Accept requests
    void accept_request(account b, account a){
        //System.out.println("Entered g.accept_request()"+a.username+","+b.username);
        a.following.add(b);
        if(a!=admin){
            b.followers.add(a);
        }
        b.requests.remove(a);
        e++;
    }
    
    // Disconnect accounts
    void disconnect(account a, account b){
        //System.out.println("Entered g.disconnect()"+a.username+","+b.username);
        if(a==b){
            System.out.println(a.username+": Invalid unfollow ");
        }
        else if(!a.following.contains(b)){
            System.out.println(a.username+" doesn't follow "+ b.username);
        }
        else{
            a.following.remove(b);
            b.followers.remove(a);
            e--;
        }
    }
    
    account get_account(String un){
        int i;
        for( i=0;i<n-1;i++){
            if(admin.following.get(i).username.equals(un) ){
                return admin.following.get(i) ;
            }
        }
        return null;
    }
    
    boolean username_exists(String un){
        for(int i=0;i<n-1;i++){
            if(admin.following.get(i).username.equals(un) ){
                return true;
            }
        }
        return false;
    }
    
    account search_byUsername(){
        String un;
        Scanner sc= new Scanner(System.in);
        System.out.print("\nEnter Username of the profile: ");
        un= sc.nextLine();
        for(int i=0;i<n-1;i++){
            if(admin.following.get(i).username.equals(un) ){
                admin.following.get(i).display_acc();
                return admin.following.get(i);
            }
        }
        System.out.println("\n----------------------------------------------------" );
        return null;
    }
    
    void display_connections(){ //following list
	    //System.out.println("Entered g.display_connections()");
	    System.out.print("\n-------------------Connections----------------------");
	    System.out.println("\n[ following list ]");
		for(int i = 2; i<=n; i ++){   
			System.out.print("\n"+acc[i].username+ " : [");
			for(int j =0 ; j< acc[i].following.size() ; j++){
			    System.out.print(" "+ acc[i].following.get(j).username+"  ");
			}
			System.out.print("]");
		}
	    System.out.println("\n----------------------------------------------------" );	
	}
	
	void show_all(){
        //System.out.println("Entered g.show_all()");
        System.out.println("\n----------ALL ACCOUNTS IN THE NETWORK--------------");
        for(int i=0;i<n-1;i++){
            System.out.print("["+admin.following.get(i).username+"]  ");
        }
        System.out.println("\n----------------------------------------------------" );
    }
    
    void show_followers(account a){
        //System.out.println(a.followers.size());
        System.out.println(a.username+ " followers: ");
        for(int i=0;i<(a.followers.size());i++){
            System.out.print("["+a.followers.get(i).username+"]  ");
        }
    }
    
    void show_mutuals(account a, account b){
        //System.out.println("Entered g.show_mutuals()"+a.username+","+b.username);
        int flag=0;    // number of mutuals found
        account max,min;
        boolean m;
        // check who has more followings
        if(a.following.size()>b.following.size()){
            max = a;
            min= b;
        }
        else{
            max=b;
            min=a;
        }   
        System.out.println("\n----------------------------------------------------" );
        System.out.println(a.username+ " & "+ b.username +" MUTUALS LIST:\n");
        for(int i =0; i<min.following.size() ; i++){
            m = max.following.contains( min.following.get(i) );
           if(m==true){
               System.out.println("["+ max.following.get(i).username+"]" );
               flag++;
           }
        }
        if(flag==0){
            System.out.println("No mutuals found.");
        }
        else{
            System.out.println("("+ flag + " mutuals found.)");
        }
        System.out.println("\n----------------------------------------------------" );
    }
        
    void get_recommendations(account a){
        //System.out.println("Entered g.get_recommendations() "+a.username);
        Queue<account> q = new LinkedList<account>();
    	boolean visited[] = new boolean[n+1];
    	visited[a.index]=true; 
    	q.add(a);
    	System.out.println("\n----------------------------------------------------" );
    	System.out.println("RECOMMENDATIONS FOR "+a.username+" :  ");
    	while(!q.isEmpty()){
    	    account v = q.poll();
    	    if((v.username==a.username) || (a.following.contains(v)) || (v==admin) ){
    	        // don't print the account itself, it's followings, admin
    	    }
    	    
    	    else{
    	        System.out.print("["+v.username+ "] ");   // not connected, hence recommend.
    	    }
    	    Iterator<account> i = acc[v.index].following.listIterator();
            while (i.hasNext()) {
                account n = i.next();
                if (!visited[n.index]) {
                  visited[n.index] = true;
                  q.add(n);
                }
    	    }
		 }
		 System.out.println("\n----------------------------------------------------" );
    }
    
    void bfsList(account a){
        Queue<account> q = new LinkedList<account>();
		boolean visited[] = new boolean[n+1];
	    visited[a.index]=true; 
		q.add(a);
		System.out.print("\n\n"+a.username+ " bfs: ");
		while(!q.isEmpty()){
		    account v = q.poll();
		    System.out.print(v.username+ "  ");
		    Iterator<account> i = acc[v.index].following.listIterator();
            while (i.hasNext()) {
                account n = i.next();
                if (!visited[n.index]) {
                  visited[n.index] = true;
                  q.add(n);
                }
		    }
		}
    }
 

}// Graph class ends

public class Main{
	public static void main(String[] args) {
	    //ArrayList<account> arrL = new ArrayList<account>();
		Scanner sc = new Scanner(System.in);
		Graph g = new Graph();
		String un,pass;
		int choice,op,i=0,lop;
		char ch;
		System.out.println("WELCOME TO OUR NETWORKING APPLICATION!!!");
		do{
		    System.out.println("\n===================================");
    		System.out.println("1.Sign up");    
        	System.out.println("2.Login "); 
        	System.out.println("3.Display existing accounts");
        	System.out.println("4.Display all connections");
        	System.out.println("0.Exit application");
        	System.out.println("===================================");
        	
        	//System.out.print("Enter option: ");
        	//op = sc.nextInt();
        	op=0;
        	try{System.out.print("Enter option: ");
        	    op = sc.nextInt();
        		int x;
                String ops=Integer.toString(op);
                x= Integer.parseInt(ops);
            }
            catch(Exception e){
                System.out.println("Enter an integer option. Try again.");
            }
    		
    		switch(op){
    		    case 1:
    		        //SIGN UP
    		        account temp=new account();
    		        temp.create_acc();
    		        g.add_account(temp);
    		       // arrL.add(temp);
    		        System.out.println("SIGN UP SUCCESSFULL!");
    		        System.out.println("Login to access your account.");
    		        i++;
    		        break;
    		        
    		    case 2:
    		        //LOGIN
    		        //check if username exists
    		        re_enter: 
    		        while(true){
    		            System.out.print("Enter username: ");
    		            un= sc.next();
        		        if(!g.username_exists(un)){
        		            System.out.println("Username doesn't exist");
        		            continue re_enter;
        		        }
        		        else {  break;  }
    		        }
    		        account login=g.get_account(un);
    		        //Enter password to login
    		        retrylogin:
    		        while(login!=null){
    		            System.out.print("Enter password: ");
    		             pass= sc.next();
        		        if(!pass.equals( g.acc[login.index].pass ) ){
        		            System.out.println("Incorrect password :/");
        		            continue retrylogin;
        		        }
        		        else{
        		            System.out.println(login.username+ " LOGGED IN SUCCESSFULLY!!");
        		            break;
        		        }
    		         }
    		         do{
    		            System.out.println("\n----------------------------------------------------");
                        System.out.println("\tMENU :");
                        System.out.println("1.Search a profile");
                        System.out.println("2.Send follow request"); //connect 
                        System.out.println("3.Accept requests"); 
                        System.out.println("4.Show followers list");
                        System.out.println("5.Unfollow an account");
                        System.out.println("6.Show follow recommendations"); 
                        System.out.println("7.Show mutuals"); 
                        System.out.println("0.Log out");
                        System.out.println("----------------------------------------------------");
                        System.out.print("Enter your choice: ");
                        lop = sc.nextInt();
                        switch(lop){
                            case 1:
                                // Search a profile
                                System.out.println("\n< active account: "+login.username+" >");
                                System.out.print("Enter username to view the profile: ");
                    		    String sun= sc.next();
                    		    g.get_account(sun).display_acc();
                                break;
                                
                            case 2:
                                // Send follow request
                                System.out.println("\n< active account: "+login.username+" >");
                                System.out.print("Enter username: ");
                    		    String bun= sc.next();
                    		    account b= g.get_account(bun);
                    		    g.connect(g.acc[login.index] ,g.get_account(bun));
                                break;
                                
                            case 3:
                                //Accept requests
                                System.out.println("\n< active account: "+login.username+" >");
                                g.show_requests(g.acc[login.index]);
                                break;
                                
                            case 4:
                                //Show followers
                                System.out.println("\n< active account: "+login.username+" >");
                                g.show_followers(g.acc[login.index]);
                                break;
                                
                            case 5:
                                //Unfollow
                                System.out.println("\n< active account: "+login.username+" >");
                                System.out.print("Enter username of account to unfollow: ");
                    		    String unun= sc.next();
                    		    account uf= g.get_account(unun);
                                g.disconnect(g.acc[login.index], g.acc[uf.index]);
                                break;
                                
                            case 6:
                                // Get recommendations
                                System.out.println("\n< active account: "+login.username+" >");
                                g.get_recommendations(g.acc[login.index]);
                                break;
                                
                            case 7:
                                // Show mutuals
                                System.out.println("\n< active account: "+login.username+" >");
                                System.out.print("Enter username to find mutuals: ");
                    		    String mun= sc.next();
                    		    account m= g.get_account(mun);
                                g.show_mutuals(g.acc[login.index],g.acc[m.index]);
                                break;
                                
                            case 0:
                                System.out.print("\n<logging off account: "+login.username+" >");
                                System.out.print("LOGGED OUT."); 
                                break;
                                
                            default:
                                System.out.print("Invalid choice :("); 
                                break;
                                
                        }// switch2
    		         }while(lop!=0); break;
    		         
    		    case 3:
    		        // Display all accounts
                    g.show_all();
    		        break;
    		        
    		    case 4:
    		        // Display AdjList
    		        g.display_connections();
    		        break;
    		        
    		    case 0:
    		        System.out.print("TERMINATED.");
    		        break;
    		        
    		    default:
    		        System.out.print("Invalid option");
    		        break;
    		    
    		}// switch1
		}while(op!=0); 
	}
}