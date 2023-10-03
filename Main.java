import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;


public class Main {
	static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Main() {
    }
    
    public static void main(String[] args){
		
		int userType ,mainMenuChoice=0, custMenuChoice=0,adminMenuChoice=0, returnMenu=0, custRecordsCount = 1,adminRecordsCount=1, reserveCount=1, currentRecord=1, paymentCount=0,reserveMenu=0;
    	char toRegister, reserveConfirm, payConfirm,adminRegisterConfirm=' ',cancelConfirm,search=' ';
    	boolean isLoginSuccessful = false;

		Scanner scanner = new Scanner(System.in);

    	Customer custRecord = new Customer();
    	Customer[] custRecords = new Customer[100];
		
		Reservation reserving=new Reservation();

		Payment paymentRecord = new Payment();

		Cancellation cancel=new Cancellation();

    	//Predefine admin account (Can be a few account(s))
		Admin admin=new Admin();
    	Admin[] adminRecords = new Admin[100];

		adminRecords[0]=new Admin("Chong Kah Jie","CKJ2021","020116070999","A2021","Admin");
		
		//predefine train details
		//=====================================================================================================================
		TrainRoute[] route={new TrainRoute(1,1),
							new TrainRoute(1,2),
							new TrainRoute(2,1),
							new TrainRoute(2,2)
		};

		Train[] train={	new Train("F0001",200,route[0]),
	   					new Train("F0002",200,route[1]),
	 	 		 		new Train("B0001",200,route[2]),
				   		new Train("B0002",200,route[3])
	   	};
		//=====================================================================================================================
		

		//predefine customer detail and reservation details for testing
		//=====================================================================================================================
		custRecords[0]= new Customer("Tan Yan Pin","1234567890","123456789012","a");
		Bank bank=new Bank();
		bank.setBankMerchant(1);

		CreditCard creditCard=new CreditCard();
		creditCard.setCreditCardMerchant(1);
		creditCard.setCreditCardNo("1234567890123456");

		Ewallet ewallet=new Ewallet();
		ewallet.setEWalletMerchant(1);

		PaymentMethod CD=new PaymentMethod("CREDITCARD");
		PaymentMethod OB=new PaymentMethod("ONLINE BANKING");
		PaymentMethod EW=new PaymentMethod("EWALLET");
		

		Reservation[] reservation={new Reservation(train[0],"Arau","Butterworth",LocalDate.of(2021, 8, 2),1),
		   						   new Reservation(train[2],"Batang Melaka","Ipoh",LocalDate.of(2021, 8, 2),2)
									};
		
		Payment payment[]={new Payment(LocalDate.of(2021, 8, 1),CD,creditCard.setCreditCardMerchant(1),reservation[0],1),
						   new Payment(LocalDate.of(2021, 10, 10),OB,bank.setBankMerchant(1),reservation[1],2)};

		reservation[0].setPayment(payment[0]);
		reservation[1].setPayment(payment[1]);
		custRecords[0].setReservation(reservation[0]);
		custRecords[0].setReservation(reservation[1]);
		updateReservation(custRecords, custRecordsCount);
		//=====================================================================================================================
		
		
		do{
		userType = selectUserType();

    	if(userType == 1){//Customer
    		do{
	    		mainMenuChoice = loginRegisterMenu();

		    	if(mainMenuChoice == 1){//Register
		    		custRecord = custRegister(custRecords,custRecordsCount);

					do{
		    			System.out.print("\nConfirm to register as our customer? (Y / N): ");
						toRegister = scanner.next().charAt(0);
					}while(checkChar(toRegister)==false);

					if(Character.toUpperCase(toRegister) == 'Y'){
						custRecords[custRecordsCount] = custRecord;
						isLoginSuccessful=false;
						custRecordsCount++;
					}
					
		    	}
		    	else{//Login
		    		currentRecord = custLogin(custRecords,custRecordsCount); //unsuccessful login will return currentRecord=-1
					isLoginSuccessful=true;
					
		    	}

    		}while(isLoginSuccessful == false||currentRecord==-1);//repeat if login unsucessfull or don't want to register

			do{
				returnMenu=0;

				custMenuChoice=custMenu();

				if(custMenuChoice==1){//make reservation
					do{
						reserveMenu=0;
						reserving=reserveMenu(train,route,reserveCount);
	
						do{
							System.out.printf("Are you sure you want to make the reservation?(Y/N):");
							reserveConfirm=scanner.next().charAt(0);
						}while(checkChar(reserveConfirm)==false);

						if(Character.toUpperCase(reserveConfirm)=='Y'){	
							
							do{
								reserveMenu=0;
								paymentRecord=makePayment(reserving,paymentCount,custRecords[currentRecord]);

								do{
									System.out.printf("\nAre you sure you want to make the payment?(Y/N):");
									payConfirm=scanner.next().charAt(0);
								}while(checkChar(payConfirm)==false);

								if(Character.toUpperCase(payConfirm)=='Y'){
									Payment.generateReceipt(reserving,paymentRecord,custRecords[currentRecord]);
									reserving.setPayment(paymentRecord);

									System.out.printf("\nEnter to continue");
									scanner.nextLine();
									scanner.nextLine();
								
									custRecords[currentRecord].setReservation(reserving);
									reserveCount++;
									paymentCount++;
									returnMenu=2;
									break;	

								}
								else{
									do{
										System.out.println("\n1. Re-enter reservation details");
										System.out.println("2. Re-enter payment details");
										System.out.println("3. Exit to main menu");
										System.out.printf("\nEnter your choice(1-3):");
										reserveMenu=scanner.nextInt();
									}while(checkInt(reserveMenu, 1, 3)==false);

									if(reserveMenu==1){
										break;
									}
									else if(reserveMenu==2){

									}
									else{
										returnMenu=2;
									}

								}
							}while(reserveMenu==2);				
						}
						else{
							do{
								System.out.println("\n1. Re-enter reservation details");
								System.out.println("2. Exit to main menu");
								System.out.printf("\nEnter your choice(1-2):");
								reserveMenu=scanner.nextInt();
							}while(checkInt(reserveMenu, 1, 2)==false);

							if(reserveMenu==2){
								returnMenu=2;//return to admin menu
							}
						}
					}while(reserveMenu==1);
				}
				else if(custMenuChoice==2){//make cancellation
					updateReservation(custRecords, custRecordsCount);


					cancel=makeCancellation(custRecords,currentRecord);
					if(cancel.getCancellationTimestamp()==null){
					}
					else{
						do{
							System.out.printf("\nDo you want to cancel your reservation? (Y / N): ");
							cancelConfirm = scanner.next().charAt(0);
			
						}while(checkChar(cancelConfirm) == false);
				
						if(Character.toUpperCase(cancelConfirm)=='Y'){
							custRecords[currentRecord].setCancellation(cancel);

							for(int i=0;i<custRecords[currentRecord].getReserveCount();i++){
								if(custRecords[currentRecord].getReservation(i).getTicketID().equals(cancel.getReservation().getTicketID())){
									Cancellation.cancelReservationProcessCust(custRecords[currentRecord].getReservation(i), cancel);
								}
							}
							System.out.printf("\nEnter to continue");
							scanner.nextLine();
							scanner.nextLine();
						}	
					}
					returnMenu=2;
				}
				else if(custMenuChoice==3){//edit customer record

					custRecord = editCustomer(custRecords,custRecordsCount,currentRecord);
					custRecords[currentRecord]=custRecord;
					returnMenu=2;//return to admin menu

				}
				else if(custMenuChoice==4){//view record
					
					viewRecord(custRecords, currentRecord);
					returnMenu=2;//return to admin menu

				}
				else if(custMenuChoice==5){//customer search

					do{
						custSearch(custRecords,currentRecord);
						
						do{
							System.out.printf("\nDo you want to search again? (Y/N): ");
							search=scanner.next().charAt(0);
						}while(checkChar(search)==false);
	
					}while(Character.toUpperCase(search)=='Y');
					
					returnMenu=2;//return to admin menu
				}
				else if(custMenuChoice==6){//return to select user menu
				}
				else{//exit program
					System.exit(0);
				}
			}while(returnMenu==2);//return to admin menu

    	}
    	else{//Admin
			isLoginSuccessful=adminLogin(adminRecords,adminRecordsCount);

			if(isLoginSuccessful==false){
				adminMenuChoice=4;
			}
			else{
			do{
				adminMenuChoice=0;//reset adminMenuChoice
				adminMenuChoice = adminMenu();

				if(adminMenuChoice == 1){//register admin
					do{
					admin=adminRegister(adminRecordsCount,adminRecords);

					do{
		    			System.out.printf("\nConfirm to register a new admin? (Y / N): ");
						toRegister = scanner.next().charAt(0);

					}while(checkChar(toRegister)==false);//check input is Y or N, if not, loop

					if(Character.toUpperCase(toRegister) == 'Y'){
						adminRecords[adminRecordsCount] = admin;
						adminRecordsCount++;

					}
					else{
						do{
							System.out.printf("\nDo you want to register again? (Y/N): ");
							adminRegisterConfirm=scanner.next().charAt(0);

						}while(checkChar(adminRegisterConfirm)==false);

					}

					adminMenuChoice=5;//return to admin menu
				}while(Character.toUpperCase(adminRegisterConfirm)=='Y');

				}
				else if(adminMenuChoice==2){//display reservation
					adminViewRecord(custRecords, custRecordsCount);
					adminMenuChoice=5;//return to admin menu
				}
				else if(adminMenuChoice==3){//search customer
					adminSearch(custRecords, custRecordsCount);
					adminMenuChoice=5;//return to admin menu
				}
				else if(adminMenuChoice==4){//return to select user menu

				}
				else{//exit program
					System.exit(0);
				}
			}while(adminMenuChoice==5);//return to admin menu
			
		}
	}
	}while(custMenuChoice==6||adminMenuChoice==4);//return to choose user type
    	
    }
    
	public static int selectUserType(){//user choose user type
		int choice;

		Scanner scanner = new Scanner(System.in);
			System.out.println("=================================");
			System.out.println("|	 _   __________  ___		|");
			System.out.println("|	| | / /_   _|  \\/  |		|");
			System.out.println("|	| |/ /  | | | .  . |		|");
			System.out.println("|	|    \\  | | | |\\/| |		|");
			System.out.println("|	| |\\  \\ | | | |  | |		|");
			System.out.println("|	\\_| \\_/ \\_/ \\_|  |_/		|");
			System.out.println("=================================");
			System.out.println("\nUser Type");
			System.out.println("=========");
			System.out.println("1. Customer");
			System.out.println("2. Admin");
			do{
			System.out.print("\nEnter your choice (1 - 2): ");
			choice = scanner.nextInt();

			
		}while(checkInt(choice, 1, 2)==false);

		return choice;
	}

	public static int custMenu(){//customer choose what to do
		int choice;
		Scanner scanner=new Scanner(System.in);

		System.out.println("\n=========================================================");
		System.out.println("|	 _    _      _                            _ 		|");
		System.out.println("|	| |  | |    | |                          | |		|");
		System.out.println("|	| |  | | ___| | ___ ___  _ __ ___   ___  | |		|");
		System.out.println("|	| |/\\| |/ _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | |		|");
		System.out.println("|	\\  /\\  /  __/ | (_| (_) | | | | | |  __/ |_|		|");
		System.out.println("|	 \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___| (_)		|");
		System.out.println("=========================================================");




		System.out.println("\n1. Make Reservation");
		System.out.println("2. Cancel Reservation");
		System.out.println("3. Modify Account Details");
		System.out.println("4. View Record");
		System.out.println("5. Search Record");
		System.out.println("6. Return to select user menu");
		System.out.println("7. Exit Program");

		do{
		System.out.printf("\nEnter your choice (1-7):");
		choice=scanner.nextInt();
		
		}while(checkInt(choice, 1, 7)==false);

		return choice;

	}

	public static int loginRegisterMenu(){//customer choose login or register
		int choice;

		Scanner scanner = new Scanner(System.in);

		System.out.println("=========================================================");
		System.out.println("|	 _____           _                            		|");		
		System.out.println("|	/  __ \\         | |                           		|");
		System.out.println("|	| /  \\/_   _ ___| |_ ___  _ __ ___   ___ _ __ 		|");
		System.out.println("|	| |   | | | / __| __/ _ \\| '_ ` _ \\ / _ \\ '__|		|");
		System.out.println("|	| \\__/\\ |_| \\__ \\ || (_) | | | | | |  __/ |   		|");
		System.out.println("|	 \\____/\\__,_|___/\\__\\___/|_| |_| |_|\\___|_|   		|");
		System.out.println("=========================================================");



		System.out.println("\n1. Register as customer");
		System.out.println("2. Login as customer");
		do{
			System.out.printf("\nEnter your choice(1 - 2): ");
			choice = scanner.nextInt();

		}while(checkInt(choice, 1, 2)==false);

		return choice;
	}

	public static Customer custRegister(Customer[] custsRecord,int custRecordCount){//customer register
		String name = "",phoneNo = "",icNo = "",password = "",reenteredPassword = "";
		int icExist=0;

		Scanner scanner = new Scanner(System.in);
		System.out.println("=================================================================");
		System.out.println("|	______           _     _             _   _             		|");
		System.out.println("|	| ___ \\         (_)   | |           | | (_)            		|");
		System.out.println("|	| |_/ /___  __ _ _ ___| |_ _ __ __ _| |_ _  ___  _ __  		|");
		System.out.println("|	|    // _ \\/ _` | / __| __| '__/ _` | __| |/ _ \\| '_ \\ 		|");
		System.out.println("|	| |\\ \\  __/ (_| | \\__ \\ |_| | | (_| | |_| | (_) | | | |		|");
		System.out.println("|	\\_| \\_\\___|\\__, |_|___/\\__|_|  \\__,_|\\__|_|\\___/|_| |_|		|");
		System.out.println("|	            __/ |                                      		|");
		System.out.println("|	           |___/                                     		|");
		System.out.println("=================================================================");

		System.out.printf("\nEnter your name       : ");
		name = scanner.nextLine();

		do{
			System.out.printf("Enter your phone no   : ");
			phoneNo = scanner.nextLine();

		}while(checkIsDigit(phoneNo)==false||checkLength(phoneNo, 12,10)==false);

		do{
			do{
				icExist=0;
				System.out.printf("Enter your Ic No      : ");
				icNo = scanner.nextLine();

			}while(checkIsDigit(icNo)==false||checkLength(icNo, 12,12)==false);

			for(int i=0;i<custRecordCount;i++){
				if(icNo.equals(custsRecord[i].getIcNo())){
					System.out.println("The IC number already exist!");
					icExist=1;
					break;
				}
			}
		}while(icExist==1);

		do{
			System.out.printf("Enter your password   : ");
			password = scanner.nextLine();

			System.out.printf("Reenter your password : ");
			reenteredPassword = scanner.nextLine();

			if(password.toUpperCase().compareTo(reenteredPassword.toUpperCase()) != 0){
				System.out.println("[Invalid]");
			}
		}while(password.toUpperCase().compareTo(reenteredPassword.toUpperCase()) != 0);

		Customer customer = new Customer(name,phoneNo,icNo,password);

		return customer;
	}

	public static int custLogin(Customer[] custRecords,int custRecordsCount){//customer login
		String IC = "",password = "";
		char reLogin;
		boolean isLoginSuccessful = false;
		int currentRecord=0;

		Scanner scanner = new Scanner(System.in);

		do{
			System.out.println("=====================================");
			System.out.println("|	 _                 _       		|");
			System.out.println("|	| |               (_)      		|");
			System.out.println("|	| |     ___   __ _ _ _ __  		|");
			System.out.println("|	| |    / _ \\ / _` | | '_ \\ 		|");
			System.out.println("|	| |___| (_) | (_| | | | | |		|");
			System.out.println("|	\\_____/\\___/ \\__, |_|_| |_|		|");
			System.out.println("|	              __/ |        		|");
			System.out.println("|	             |___/        		|");
			System.out.println("=====================================");
			System.out.printf("\nEnter your IC     : ");
			IC = scanner.nextLine();
		
			System.out.printf("Enter your password : ");
			password = scanner.nextLine();

			for(int i = 0;i < custRecordsCount;i++){
				if(custRecords[i].getIcNo().toUpperCase().compareTo(IC.toUpperCase()) == 0 && custRecords[i].getPassword().compareTo(password) == 0){
					System.out.println("\nLogin successfully !");
					isLoginSuccessful = true;
					currentRecord=i;
					break;
				}
			
			}

			if(isLoginSuccessful == false){
				System.out.println("\nInvalid name or password\n");

				do{
					System.out.printf("Do you wish to login again? (Y / N): ");
					reLogin = scanner.next().charAt(0);
					scanner.nextLine();

					if(Character.toUpperCase(reLogin)=='N'){
						currentRecord=-1;
					}

				}while(checkChar(reLogin)==false);
				
			}
			else{
				reLogin='N';
			}
			
		}while(Character.toUpperCase(reLogin) == 'Y');

		return currentRecord;
	}

	public static Customer editCustomer(Customer[] custRecords,int custRecordsCount,int currentRecord){
		char toEditRecord;
		String name = "",phoneNo = "",password = "",reenteredPassword = "";
		
		Scanner scanner = new Scanner(System.in);

			
			System.out.println("=========================================================================");
			System.out.println("|	 _____    _ _ _     _____           _                            	|");
			System.out.println("|	|  ___|  | (_) |   /  __ \\         | |                           	|");
			System.out.println("|	| |__  __| |_| |_  | /  \\/_   _ ___| |_ ___  _ __ ___   ___ _ __ 	|");
			System.out.println("|	|  __|/ _` | | __| | |   | | | / __| __/ _ \\| '_ ` _ \\ / _ \\ '__|	|");
			System.out.println("|	| |__| (_| | | |_  | \\__/\\ |_| \\__ \\ || (_) | | | | | |  __/ |   	|");
			System.out.println("|	\\____/\\__,_|_|\\__|  \\____/\\__,_|___/\\__\\___/|_| |_| |_|\\___|_|   	|");
			System.out.println("=========================================================================");
			
			
			
			System.out.println("\nCurrent Customer Details");
			System.out.println("---------------------------");
			System.out.printf("Current name     : %s\n",custRecords[currentRecord].getName());
			System.out.printf("Current phone no : %s\n",custRecords[currentRecord].getPhoneNo());
			System.out.printf("Current Ic No    : %s\n",custRecords[currentRecord].getIcNo());
			System.out.printf("Current password : %s\n\n",custRecords[currentRecord].getPassword());


			System.out.printf("Enter new name       : ");
			name = scanner.nextLine();

			do{
				System.out.print("Enter new phone no   : ");
				phoneNo = scanner.nextLine();
			}while(checkIsDigit(phoneNo)==false||checkLength(phoneNo, 12, 10)==false);

			do{
				System.out.printf("Enter new password   : ");
				password = scanner.nextLine();

				System.out.printf("Reenter new password : ");
				reenteredPassword = scanner.nextLine();

				if(password.toUpperCase().compareTo(reenteredPassword.toUpperCase()) != 0){
					System.out.println("[Invalid]");
				}
			}while(password.toUpperCase().compareTo(reenteredPassword.toUpperCase()) != 0);

			do{
				System.out.printf("Confirm to edit account details? (Y / N): ");
				toEditRecord = scanner.next().charAt(0);

			}while(checkChar(toEditRecord)==false);

			if(Character.toUpperCase(toEditRecord) == 'Y'){
				custRecords[currentRecord].setName(name);
				custRecords[currentRecord].setPhoneNo(phoneNo);
				custRecords[currentRecord].setPassword(password);
			}

		return custRecords[currentRecord];
	}

    public static Reservation reserveMenu(Train[] train,TrainRoute[] route,int reserveCount){ 
		String origin,destination,direction,departDate;
		int error=0, oriChoice=0, destChoice=0,originRef=0,destRef=0,scheduleInput,sequence=0,seatCount=0;
		Scanner scanner=new Scanner(System.in);

		System.out.println("\n=================================================================");
		System.out.println("|	______                               _   _             		|");
		System.out.println("|	| ___ \\                             | | (_)            		|");
		System.out.println("|	| |_/ /___  ___  ___ _ ____   ____ _| |_ _  ___  _ __  		|");
		System.out.println("|	|    // _ \\/ __|/ _ \\ '__\\ \\ / / _` | __| |/ _ \\| '_ \\ 		|");
		System.out.println("|	| |\\ \\  __/\\__ \\  __/ |   \\ V / (_| | |_| | (_) | | | |		|");
		System.out.println("|	\\_| \\_\\___||___/\\___|_|    \\_/ \\__,_|\\__|_|\\___/|_| |_|		|");
		System.out.println("=================================================================");




    	System.out.println("\nStation locations");
		System.out.println("==================");
    	System.out.printf("1. Arau\n");
    	System.out.printf("2. Alor Setar\n");
    	System.out.printf("3. Butterworth\n");
    	System.out.printf("4. Ipoh\n");
    	System.out.printf("5. Sungai Buloh\n");
    	System.out.printf("6. KL Sentral\n");
    	System.out.printf("7. Seremban\n");
    	System.out.printf("8. Batang Melaka\n");

		do{
			do{
    			System.out.printf("\nEnter your origin location(in integer)\t: ");
				oriChoice=scanner.nextInt();
		
				switch(oriChoice){
					case 1:origin="Arau";error=0;break;
					case 2:origin="Alor Setar";error=0;break;
					case 3:origin="Butterworth";error=0;break;
					case 4:origin="Ipoh";error=0;break;
					case 5:origin="Sungai Buloh";error=0;break;
					case 6:origin="KL Sentral";error=0;break;
					case 7:origin="Seremban";error=0;break;
					case 8:origin="Batang Melaka";error=0;break;
					default:System.out.println("Please enter again");origin=" ";error=1;break;
				}
			}while(error==1);
			error=0;
			
			do{
			System.out.printf("Enter your destination(in integer)\t\t: ");
			destChoice=scanner.nextInt();
			scanner.nextLine();
			switch(destChoice){
				case 1:destination="Arau";error=0;break;
				case 2:destination="Alor Setar";error=0;break;
				case 3:destination="Butterworth";error=0;break;
				case 4:destination="Ipoh";error=0;break;
				case 5:destination="Sungai Buloh";error=0;break;
				case 6:destination="KL Sentral";error=0;break;
				case 7:destination="Seremban";error=0;break;
				case 8:destination="Batang Melaka";error=0;break;
				default:System.out.println("Please enter again");destination=" ";error=1;break;
			}
			}while(error==1);
			error=0;

			if(destChoice==oriChoice){
				System.out.printf("Error, origin cannot be the same with destination\n");
				error=1;
			}
			else{
			error=0;
			}

		}while(error==1);
		error=0;
		do{
			System.out.printf("Enter the depart date(d/m/yyyy)\t\t\t: ");
			departDate=scanner.nextLine();

			if(stringToDate(departDate)==LocalDate.now()||stringToDate(departDate).compareTo(LocalDate.now())<1){
				System.out.println("Date cannot be before or same date as today!");
			}
		}while(stringToDate(departDate)==LocalDate.now()||stringToDate(departDate).compareTo(LocalDate.now())<1);

		if(oriChoice>destChoice){
			direction="backward";
		}
		else{
			direction="forward";
		}

		for(int j=0;j<route.length;j++){

			if(direction==route[j].getDirection()){
				
				for(int i=0;i<route[j].getRouteLength();i++){

					if(origin==route[j].getRoute(i)){
						originRef=i;	
					}
					else if(destination==route[j].getRoute(i)){
						destRef=i;
					}
				}
			}
		}
		
		int scheduleChoice[]=new int[route.length];

		System.out.printf("--------------------------------------------------------------------------------------------\n");
		System.out.printf("|No Train ID\tOrigin\t\t\tDestination\t\tDepart Time\t\tArrive Time\t\tSeat Counts|\n");
		System.out.printf("--------------------------------------------------------------------------------------------\n");

		for(int j=0;j<route.length;j++){

			if(direction==route[j].getDirection()){

				if(train[j].getSeatCount()>0){
					sequence++;
					scheduleChoice[sequence]=j;
					System.out.printf(" %d. %-12s%-16s%-16s%-16s%-16s%d\n",sequence,train[j].getTrainID(),route[j].getRoute(originRef),route[j].getRoute(destRef),route[j].getSchedule(originRef),route[j].getSchedule(destRef),train[j].getSeatCount());
				}
				else{

				}
			}
		}
		do{
		System.out.printf("\nEnter your choice (1-2):");
		scheduleInput=scanner.nextInt();
		}while(checkInt(scheduleInput, 1, 2)==false);

		seatCount=train[scheduleChoice[scheduleInput]].getSeatCount();
		seatCount--;
		train[scheduleChoice[scheduleInput]].setSeatCount(seatCount);
		System.out.printf("\n------------------------------------------------------------------------\n");
		System.out.printf("|Train ID\tOrigin\t\t\tDestination\t\tDepart Time\t\tArrive Time|\n");
		System.out.printf("------------------------------------------------------------------------\n");
		System.out.printf(" %-11s%-16s%-16s%-16s%-16s\n\n",train[scheduleChoice[scheduleInput]].getTrainID(),route[scheduleChoice[scheduleInput]].getRoute(originRef),route[scheduleChoice[scheduleInput]].getRoute(destRef),route[scheduleChoice[scheduleInput]].getSchedule(originRef),route[scheduleChoice[scheduleInput]].getSchedule(destRef));
		
		Reservation reserve=new Reservation(train[scheduleChoice[scheduleInput]],origin,destination,stringToDate(departDate),reserveCount);
		
		return reserve;

    }
	
	public static void viewRecord(Customer[] custRecords,int currentRecord){//view record menu
		int choice;

		Scanner scanner=new Scanner(System.in);
		System.out.println("=================================================================");
		System.out.println("|	 _   _ _                ______                       _ 		|");
		System.out.println("|	| | | (_)               | ___ \\                     | |		|");
		System.out.println("|	| | | |_  _____      __ | |_/ /___  ___ ___  _ __ __| |		|");
		System.out.println("|	| | | | |/ _ \\ \\ /\\ / / |    // _ \\/ __/ _ \\| '__/ _` |		|");
		System.out.println("|	\\ \\_/ / |  __/\\ V  V /  | |\\ \\  __/ (_| (_) | | | (_| |		|");
		System.out.println("|	 \\___/|_|\\___| \\_/\\_/   \\_| \\_\\___|\\___\\___/|_|  \\__,_|		|");
		System.out.println("=================================================================");

		System.out.println("\n1. View Reservation");
		System.out.println("2. View Cancellation");
		System.out.println("3. Exit");
		do{
			System.out.printf("\nEnter your Choice (1-3): ");
			choice=scanner.nextInt();
		}while(checkInt(choice, 1, 3)==false);

		if(choice==1){
			viewReservation(custRecords, currentRecord);
			System.out.printf("\nEnter to continue");
			scanner.nextLine();
			scanner.nextLine();

		}
		else if(choice==2){
			viewCancellation(custRecords, currentRecord);
			System.out.printf("\nEnter to continue");
			scanner.nextLine();
			scanner.nextLine();

		}
	}

	public static void viewReservation(Customer[] custRecords,int currentRecord){
		
		System.out.println("\nView Reservation");
		System.out.println("=================");
		
		System.out.println("\n---------------------------------------------------------------------------------------------------------");
		System.out.println("|Ticket ID\t\tOrigin\t\t\tDestination\t\tDepart Date\t\tDepart Time\t\tArrive Time\t\tStatus  |");
		System.out.println("---------------------------------------------------------------------------------------------------------");
		for(int i=0;i<custRecords[currentRecord].getReserveCount();i++){

			System.out.println(custRecords[currentRecord].getReservation(i).toString());

		}
	}

	public static void viewCancellation(Customer[] custRecords,int currentRecord){
		int sequence=1;

		System.out.println("\n---------------------------------------------------------------");
		System.out.println("|No Ticket ID\tCancellation Time\tRefund Amount\tPayment ID|");
		System.out.println("---------------------------------------------------------------");
		for(int i=0;i<custRecords[currentRecord].getReserveCount();i++){

			if(custRecords[currentRecord].getReservation(i).getStatus()=="CANCELLED"){

				for(int j=0;j<custRecords[currentRecord].getCancelCount();j++){

					if(custRecords[currentRecord].getReservation(i).getTicketID()==custRecords[currentRecord].getCancellation(j).getReservation().getTicketID()){
						System.out.printf(" %d. %-12s%s%-10s\n", sequence, custRecords[currentRecord].getCancellation(j).getReservation().getTicketID(), custRecords[currentRecord].getCancellation(j).toString(), custRecords[currentRecord].getCancellation(j).getReservation().getPayment().getPaymentId());
						sequence++;
					}
					
				}
			}

		}
	}

	public static Payment makePayment(Reservation reservation,int paymentCount,Customer custRecord){
    	int methodChoice = 0,bankChoice,creditCardChoice,ewalletChoice;
		String paymentMethod,ccNo,paymentMerchant;
    	PaymentMethod payMethod;
		Payment invoice=new Payment(reservation);
		Payment paymentRecord=new Payment();

		System.out.println("\n=====================================================");
		System.out.println("|	______                                _   		|");
		System.out.println("|	| ___ \\                              | |  		|");
		System.out.println("|	| |_/ /_ _ _   _ _ __ ___   ___ _ __ | |_ 		|");
		System.out.println("|	|  __/ _` | | | | '_ ` _ \\ / _ \\ '_ \\| __|		|");
		System.out.println("|	| | | (_| | |_| | | | | | |  __/ | | | |_ 		|");
		System.out.println("|	\\_|  \\__,_|\\__, |_| |_| |_|\\___|_| |_|\\__|		|");
		System.out.println("|	            __/ |                         		|");
		System.out.println("|	           |___/                       	   		|");
		System.out.println("=====================================================");



    	Scanner scanner = new Scanner(System.in);
		do{
		System.out.println("\nPayment method");
    	System.out.println("==============");
    	System.out.println("1. Online Banking");
    	System.out.println("2. Credit Card");
    	System.out.println("3. E-Wallet");
    	System.out.printf("\nEnter your choice (1 - 3): ");
		methodChoice = scanner.nextInt();
		}while(checkInt(methodChoice ,1, 3)==false);
		Payment.generateInvoice(reservation,invoice,custRecord);

		
			if(methodChoice==1){
				paymentMethod="ONLINE BANKING";
				payMethod=new PaymentMethod(paymentMethod);	

				Bank bank=new Bank();
				do{
				Bank.displayBanksAvailable();
				bankChoice=scanner.nextInt();
				}while(checkInt(bankChoice, 1, 10)==false);

				paymentMerchant=bank.setBankMerchant(bankChoice);
				

			}
			else if(methodChoice==2){
				paymentMethod="CREDITCARD";
				payMethod=new PaymentMethod(paymentMethod);	

				CreditCard creditCard=new CreditCard();

				do{
					CreditCard.displayCreditCardAvailable();
					creditCardChoice=scanner.nextInt();
				}while(checkInt(creditCardChoice, 1, 2)==false);

				paymentMerchant=creditCard.setCreditCardMerchant(creditCardChoice);
				
				do{
					System.out.printf("\nEnter your credit card number (16 digits): ");
					scanner.nextLine();
					ccNo = scanner.next();
					

				}while(checkIsDigit(ccNo) == false || checkLength(ccNo,16,16) == false);

				creditCard.setCreditCardNo(ccNo);

			}
			else if(methodChoice==3){
				paymentMethod="EWALLET"; 
				payMethod=new PaymentMethod(paymentMethod);

				Ewallet ewallet=new Ewallet();

				do{
					Ewallet.displayEwalletAvailable();
					ewalletChoice=scanner.nextInt();
				}while(checkInt(ewalletChoice, 1, 3)==false);

				paymentMerchant=ewallet.setEWalletMerchant(ewalletChoice);	
				
			}
			else{
				paymentMethod="";
				payMethod=new PaymentMethod(paymentMethod);	
				paymentMerchant="";
			}
		

		paymentRecord = new Payment(LocalDate.now(),payMethod,paymentMerchant,reservation,paymentCount);
			
		System.out.println("\nPayment details");
		System.out.println("--------------------------------------------------------------------------------------------------");
		System.out.println("|Payment Id\t\tAmount(RM)\t\tDate\t\t\tPayment Method\t\tPayment Merchant\tTicket Id|");
		System.out.println("--------------------------------------------------------------------------------------------------");
		System.out.println(paymentRecord.toString(reservation));


		return paymentRecord;
	}

	public static Cancellation makeCancellation(Customer[] custRecords,int currentRecord){
		String CancelTicketID;
		char confirmation=' ';
		boolean recordFound =false;
		int recordIndex=0;
		Cancellation cancellation=new Cancellation();
		
		
		Scanner scanner=new Scanner(System.in);
		System.out.println("=================================================================");
		System.out.println("|	 _____                      _ _       _   _             	|");
		System.out.println("|	/  __ \\                    | | |     | | (_)            	|");
		System.out.println("|	| /  \\/ __ _ _ __   ___ ___| | | __ _| |_ _  ___  _ __  	|");
		System.out.println("|	| |    / _` | '_ \\ / __/ _ \\ | |/ _` | __| |/ _ \\| '_ \\ 	|");
		System.out.println("|	| \\__/\\ (_| | | | | (_|  __/ | | (_| | |_| | (_) | | | |	|");
		System.out.println("|	 \\____/\\__,_|_| |_|\\___\\___|_|_|\\__,_|\\__|_|\\___/|_| |_|	|");
		System.out.println("=================================================================");
		
		do{	
			confirmation='N';
			System.out.println("\n--------------------------------------------------------------------------------------------------------");
			System.out.println("|Ticket ID\t\tOrigin\t\t\tDestination\t\tDepart Date\t\tDepart Time\t\tArrive Time\t\tStatus  |");
			System.out.println("--------------------------------------------------------------------------------------------------------");
					
			for(int i=0;i<custRecords[currentRecord].getReserveCount();i++){
				if(custRecords[currentRecord].getReservation(i).getStatus()=="ACTIVE"){
					System.out.println(custRecords[currentRecord].getReservation(i).toString());
				}

			}
			System.out.println("\nEnter to continue");
			
			
			
			scanner.nextLine();
			System.out.printf("\nPlease enter Ticket ID: ");
			CancelTicketID= scanner.nextLine();
		
			for(int i = 0;i < custRecords[currentRecord].getReserveCount();i++){
				if(CancelTicketID.toUpperCase().compareTo(custRecords[currentRecord].getReservation(i).getTicketID()) == 0){

				recordIndex = i;
				recordFound = true;
				break;
				}	
			}

			if(recordFound == true){
				Payment.generateReceipt(custRecords[currentRecord].getReservation(recordIndex),custRecords[currentRecord].getReservation(recordIndex).getPayment(),custRecords[currentRecord]);
			}
			if(recordFound == false){
				System.out.println("\nRecord not found!");


				do{
					System.out.printf("\nDo you want to search again? (Y/N): ");
					confirmation=scanner.next().charAt(0);
				}while(checkChar(confirmation)==false);

			}

		}while(Character.toUpperCase(confirmation)=='Y');
		if(recordFound==true){
			cancellation=new Cancellation(LocalDate.now(),custRecords[currentRecord].getReservation(recordIndex));
		}

		return cancellation;
		
	}

	public static void custSearch(Customer[] custRecords,int currentRecord){
		int choice,found=0;
		String reserveID="",paymentID="";

		Scanner scanner=new Scanner(System.in);
		
		System.out.println("=========================================");
		System.out.println("|	 _____                     _     	|");
		System.out.println("|	/  ___|                   | |    	|");
		System.out.println("|	\\ `--.  ___  __ _ _ __ ___| |__  	|");
		System.out.println("|	 `--. \\/ _ \\/ _` | '__/ __| '_ \\ 	|");
		System.out.println("|	/\\__/ /  __/ (_| | | | (__| | | |	|");
		System.out.println("|	\\____/ \\___|\\__,_|_|  \\___|_| |_|	|");
		System.out.println("=========================================");
		
		
		do{
			System.out.println("\n1.Reservation ID");
			System.out.println("2.Payment ID");
			System.out.println("3. Exit");
			System.out.printf("\nEnter your choice: ");
			choice=scanner.nextInt();
			scanner.nextLine();
		}while(checkInt(choice, 1, 3)==false);
		if(choice==1){
			
			System.out.println("\n---------------------------------------------------------------------------------------------------------");
			System.out.println("|Ticket ID\t\tOrigin\t\t\tDestination\t\tDepart Date\t\tDepart Time\t\tArrive Time\t\tStatus  |");
			System.out.println("---------------------------------------------------------------------------------------------------------");
		
			for(int i=0;i<custRecords[currentRecord].getReserveCount();i++){
				System.out.println(custRecords[currentRecord].getReservation(i).toString());
			}

			System.out.printf("\nEnter the Ticket ID you wish to search: ");
			reserveID=scanner.nextLine();

			for(int i=0;i<custRecords[currentRecord].getReserveCount();i++){
				if(reserveID.equals(custRecords[currentRecord].getReservation(i).getTicketID())){
					
					Payment.generateReceipt(custRecords[currentRecord].getReservation(i), custRecords[currentRecord].getReservation(i).getPayment(), custRecords[currentRecord]);
					found=1;
					if(custRecords[currentRecord].getReservation(i).getStatus()=="CANCELLED"){

						for(int j=0;j<custRecords[currentRecord].getCancelCount();j++){

							if(custRecords[currentRecord].getReservation(i).getTicketID()==custRecords[currentRecord].getCancellation(j).getReservation().getTicketID()){
								Cancellation.displayCancellation(custRecords[currentRecord].getReservation(i));
							}
						}
					}			
				}
				
			}
			if(found==0){
				System.out.printf("\nRecord not found!\n");
			}
			else{

			}

		}
		else if(choice==2){
			
			System.out.println("\n--------------------------------------------------------------------------------------------------");
			System.out.println("|Payment Id\t\tAmount(RM)\t\tDate\t\t\tPayment Method\t\tPayment Merchant\tTicket Id|");
			System.out.println("--------------------------------------------------------------------------------------------------");
			
			for(int i=0;i<custRecords[currentRecord].getReserveCount();i++){
				System.out.println(custRecords[currentRecord].getReservation(i).getPayment().toString(custRecords[currentRecord].getReservation(i)));
			}

			System.out.printf("\nEnter the payment ID: ");
			paymentID=scanner.nextLine();

			for(int i=0;i<custRecords[currentRecord].getReserveCount();i++){

				if(paymentID.equals(custRecords[currentRecord].getReservation(i).getPayment().getPaymentId())){
					Payment.generateReceipt(custRecords[currentRecord].getReservation(i), custRecords[currentRecord].getReservation(i).getPayment(), custRecords[currentRecord]);
					found=1;
					if(custRecords[currentRecord].getReservation(i).getStatus()=="CANCELLED"){

						for(int j=0;j<custRecords[currentRecord].getCancelCount();j++){

							if(custRecords[currentRecord].getReservation(i).getTicketID()==custRecords[currentRecord].getCancellation(j).getReservation().getTicketID()){
								Cancellation.displayCancellation(custRecords[currentRecord].getReservation(i));
							}
						}
					}	
				}
			}
			if(found==0){
				System.out.printf("Record not found!");
			}
		}
		
	}

	public static int adminMenu(){//admin choose what to do
		int choice;

		Scanner scanner = new Scanner(System.in);

			System.out.println("=========================================");
			System.out.println("|	  ___      _           _       		|");
			System.out.println("|	 / _ \\    | |         (_)      		|");
			System.out.println("|	/ /_\\ \\ __| |_ __ ___  _ _ __  		|");
			System.out.println("|	|  _  |/ _` | '_ ` _ \\| | '_ \\ 		|");
			System.out.println("|	| | | | (_| | | | | | | | | | |		|");
			System.out.println("|	\\_| |_/\\__,_|_| |_| |_|_|_| |_|		|");
			System.out.println("=========================================");
			


			System.out.println("\n1. Register account");
			System.out.println("2. View record");
			System.out.println("3. Search Customer");
			System.out.println("4. Exit to select user menu");
			System.out.println("5. Exit program");
			do{
			System.out.printf("\nEnter your choice (1-5): ");
			choice = scanner.nextInt();

		}while(checkInt(choice, 1, 5)==false);

		return choice;
	}

	public static boolean adminLogin(Admin[] adminRecords,int adminRecordsCount){
		String adminId = "",password = "";
		char reLogin;
		boolean isLoginSuccessful = false;

		Scanner scanner = new Scanner(System.in);
		
		do{
			System.out.println("\n=================================================================");
			System.out.println("|	  ___      _           _         _                 _       	|");
			System.out.println("|	 / _ \\    | |         (_)       | |               (_)      	|");
			System.out.println("|	/ /_\\ \\ __| |_ __ ___  _ _ __   | |     ___   __ _ _ _ __  	|");
			System.out.println("|	|  _  |/ _` | '_ ` _ \\| | '_ \\  | |    / _ \\ / _` | | '_ \\ 	|");
			System.out.println("|	| | | | (_| | | | | | | | | | | | |___| (_) | (_| | | | | |	|");
			System.out.println("|	\\_| |_/\\__,_|_| |_| |_|_|_| |_| \\_____/\\___/ \\__, |_|_| |_|	|");
			System.out.println("|	                                              __/ |        	|");
			System.out.println("|	                                             |___/         	|");
			System.out.println("=================================================================");

			

			System.out.printf("\nEnter your admin Id : ");
			adminId = scanner.nextLine();
			
			System.out.printf("Enter your password : ");
			password = scanner.nextLine();

			for(int i = 0;i < adminRecordsCount;i++){
				if(adminRecords[i].getAdminId().toUpperCase().equals(adminId.toUpperCase()) == true && adminRecords[i].getPassword().equals(password) == true){
					System.out.println("\nLogin successfully\n");
					isLoginSuccessful = true;
					break;
				}
			}

			if(isLoginSuccessful == false){
				System.out.println("\nInvalid name or password\n");

				do{
					System.out.print("Do you wish to login again? (Y / N): ");
					reLogin = scanner.next().charAt(0);
					scanner.nextLine();

				}while(checkChar(reLogin)==false);

			}
			else{
				reLogin='N';
			}

		}while(Character.toUpperCase(reLogin) == 'Y');

		return isLoginSuccessful;
	}

	public static Admin adminRegister(int adminRecordsCount,Admin[] admins){
		String adminName, password,reenteredPass, icNo, adminID,position="";
		int error=0;
		Scanner scanner=new Scanner(System.in);
		System.out.println("=============================================================================================");
		System.out.println("|	  ___      _           _        ______           _     _             _   _             	|");
		System.out.println("|	 / _ \\    | |         (_)       | ___ \\         (_)   | |           | | (_)            	|");
		System.out.println("|	/ /_\\ \\ __| |_ __ ___  _ _ __   | |_/ /___  __ _ _ ___| |_ _ __ __ _| |_ _  ___  _ __  	|");
		System.out.println("|	|  _  |/ _` | '_ ` _ \\| | '_ \\  |    // _ \\/ _` | / __| __| '__/ _` | __| |/ _ \\| '_ \\ 	|");
		System.out.println("|	| | | | (_| | | | | | | | | | | | |\\ \\  __/ (_| | \\__ \\ |_| | | (_| | |_| | (_) | | | |	|");
		System.out.println("|	\\_| |_/\\__,_|_| |_| |_|_|_| |_| \\_| \\_\\___|\\__, |_|___/\\__|_|  \\__,_|\\__|_|\\___/|_| |_|	|");
		System.out.println("|	                                            __/ |                                      	|");
		System.out.println("|	                                           |___/                                       	|");
		System.out.println("=============================================================================================");

		System.out.printf("\nEnter your name       				: ");
		adminName = scanner.nextLine();

		do{
			error=0;
			System.out.printf("Enter your admin ID (eg: B1234 )	: ");
			adminID = scanner.nextLine();

			for(int i=0;i<adminRecordsCount;i++){

				if(adminID.equals(admins[i].getAdminId())==true){
					System.out.println("Error, the Admin Id already exist!");
					error=1;
					break;
				}
			}

			if(adminID.matches("[A-Z][0-9][0-9][0-9][0-9]")==false){
				System.out.println("Invalid format!");
				error=1;
			}

		}while(error==1);

		System.out.printf("Enter your position					: ");
		position=scanner.nextLine();

		do{
			System.out.printf("Enter your Ic No      				: ");
			icNo = scanner.nextLine();

		}while(checkIsDigit(icNo)==false||checkLength(icNo, 12,12)==false);

		do{
			System.out.printf("Enter your password   				: ");
			password = scanner.nextLine();

			System.out.printf("Reenter your password 				: ");
			reenteredPass = scanner.nextLine();

			if(password.toUpperCase().compareTo(reenteredPass.toUpperCase()) != 0){
				System.out.println("\nInvalid password!\n");
			}
		}while(password.toUpperCase().compareTo(reenteredPass.toUpperCase()) != 0);

		Admin admin = new Admin(adminName,password,icNo,adminID,position);

		return admin;
	}

	public static void adminViewRecord(Customer[] custRecords,int custRecordsCount){
		int choice;

		Scanner scanner=new Scanner(System.in);
		System.out.println("=================================================================");
		System.out.println("|	 _   _ _                ______                       _ 		|");
		System.out.println("|	| | | (_)               | ___ \\                     | |		|");
		System.out.println("|	| | | |_  _____      __ | |_/ /___  ___ ___  _ __ __| |		|");
		System.out.println("|	| | | | |/ _ \\ \\ /\\ / / |    // _ \\/ __/ _ \\| '__/ _` |		|");
		System.out.println("|	\\ \\_/ / |  __/\\ V  V /  | |\\ \\  __/ (_| (_) | | | (_| |		|");
		System.out.println("|	 \\___/|_|\\___| \\_/\\_/   \\_| \\_\\___|\\___\\___/|_|  \\__,_|		|");
		System.out.println("=================================================================");

		System.out.println("\n1. Reservation Record");
		System.out.println("2. Payment Record");
		System.out.println("3. Cancellation Record");
		System.out.println("4. Exit");

		do{
			System.out.printf("\nEnter your choice (1-4): ");
			choice=scanner.nextInt();
		}while(checkInt(choice, 1, 4)==false);

		if(choice==1){
			System.out.println();
			adminReservation(custRecordsCount, custRecords);
			System.out.printf("\nEnter to continue");
			scanner.nextLine();
			scanner.nextLine();
		}
		else if(choice==2){
			System.out.println();
			adminPayment(custRecordsCount, custRecords);
			System.out.printf("\nEnter to continue");
			scanner.nextLine();
			scanner.nextLine();
		}
		else if(choice==3){
			System.out.println();
			adminCancel(custRecordsCount, custRecords);
			System.out.printf("\nEnter to continue");
			scanner.nextLine();
			scanner.nextLine();
		}
		else{
		}

	}

	public static void adminReservation(int custRecordsCount,Customer[] custRecords){
		int sequence=1,completeCount=0,activeCount=0,cancelledCount=0;
		System.out.println("\n-------------------------------------------------------------------------------------------------------------------------");
		System.out.println("|No Name\t\tTicket ID\t\tOrigin\t\t\tDestination\t\tDepart Date\t\tDepart Time\t\tArrive Time\t\tStatus  |");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------");
		for(int i=0;i<custRecordsCount;i++){
			
			for(int j=0;j<custRecords[i].getReserveCount();j++){
				System.out.printf(" %d. %-11s%s\n",sequence, custRecords[i].getName(), custRecords[i].getReservation(j).toString());
				sequence++;

				if(custRecords[i].getReservation(j).getStatus()=="COMPLETE"){
					completeCount++;
				}
				else if(custRecords[i].getReservation(j).getStatus()=="ACTIVE"){
					activeCount++;
				}
				else{
					cancelledCount++;
				}
			}
		}
		
		System.out.printf("\nTotal active reservation\t: %d",activeCount);
		System.out.printf("\nTotal Completed reservation\t: %d",completeCount);
		System.out.printf("\nTotal cancelled reservation\t: %d\n",cancelledCount);


	}

	public static void adminPayment(int custRecordsCount,Customer[] custRecords){
		int sequence=1, bank=0,creditCard=0,eWallet=0;
		
		System.out.println("------------------------------------------------------------------------------------------------------");
		System.out.println("|No  Payment Id\t\tAmount(RM)\t\tDate\t\t\tPayment Method\t\tPayment Merchant\tTicket Id|");
		System.out.println("------------------------------------------------------------------------------------------------------");
		
		for(int i=0;i<custRecordsCount;i++){
			for(int j=0;j<custRecords[i].getReserveCount();j++){
				System.out.printf(" %d. %s\n",sequence, custRecords[i].getReservation(j).getPayment().toString(custRecords[i].getReservation(j)));
				sequence++;

				if( custRecords[i].getReservation(j).getPayment().getPaymentMethod().getPaymentMethod()=="ONLINE BANKING"){
					bank++;
				}
				else if(custRecords[i].getReservation(j).getPayment().getPaymentMethod().getPaymentMethod()=="CREDITCARD"){
					creditCard++;
				}
				else if(custRecords[i].getReservation(j).getPayment().getPaymentMethod().getPaymentMethod()=="EWALLET"){
					eWallet++;
				}
			}
		}
		System.out.println("\nPayment Method used");
		System.out.println("====================");
		System.out.printf("Online Banking\t: %d\n",bank);
		System.out.printf("Credit Card\t\t: %d\n",creditCard);
		System.out.printf("E-wallet\t\t: %d\n",eWallet);
	}

	public static void adminCancel(int custRecordsCount,Customer[] custRecords){
		int sequence=1;
		System.out.println("---------------------------------------------------------------");
		System.out.println("|No Ticket ID\tCancellation Time\tRefund Amount\tPayment ID|");
		System.out.println("---------------------------------------------------------------");

		for(int i=0;i<custRecordsCount;i++){
			for(int b=0;b<custRecords[i].getReserveCount();b++){

				if(custRecords[i].getReservation(b).getStatus()=="CANCELLED"){

					for(int j=0;j<custRecords[i].getCancelCount();j++){

						if(custRecords[i].getReservation(b).getTicketID()==custRecords[i].getCancellation(j).getReservation().getTicketID()){
							System.out.printf(" %d. %s%-12s%s%-10s\n",sequence, custRecords[i].getName(),custRecords[i].getCancellation(j).getReservation().getTicketID(), custRecords[i].getCancellation(j).toString(), custRecords[i].getCancellation(j).getReservation().getPayment().getPaymentId());
							sequence++;
						}
					}
				}
			}
		}

	}

	public static void adminSearch(Customer[] customer,int custRecordsCount){
		char choice,repeat=' ';
		String ic, reserveID,paymentID;
		int searchChoice,recordFound=0,statusChoice,custFound=0;

		Scanner scanner = new Scanner(System.in);
		
		System.out.println("=========================================");
		System.out.println("|	 _____                     _     	|");
		System.out.println("|	/  ___|                   | |    	|");
		System.out.println("|	\\ `--.  ___  __ _ _ __ ___| |__  	|");
		System.out.println("|	 `--. \\/ _ \\/ _` | '__/ __| '_ \\ 	|");
		System.out.println("|	/\\__/ /  __/ (_| | | | (__| | | |	|");
		System.out.println("|	\\____/ \\___|\\__,_|_|  \\___|_| |_|	|");
		System.out.println("=========================================");

		
		System.out.println("\nCustomer Details");
		System.out.println("=================");
		System.out.println("---------------------------------------------");
		System.out.println("|Name\t\t\tPhone Number\tIC number   |");
		System.out.println("---------------------------------------------");
			
		for(int i=0;i<custRecordsCount;i++){
			System.out.printf(" %-15s%-16s%s\n", customer[i].getName(), customer[i].getPhoneNo(), customer[i].getIcNo());
		}

		System.out.printf("\nEnter the customer IC you wish to search :");
		ic=scanner.nextLine();

		for(int j=0;j<custRecordsCount;j++){
			if(ic.equals(customer[j].getIcNo())){
				custFound=1;
				System.out.println("\nCustomer Details");
				System.out.println("====================");
				System.out.printf("Name\t\t\t: %s\n",customer[j].getName());
				System.out.printf("IC Number\t\t: %s\n",customer[j].getIcNo());
				System.out.printf("Phone Number\t: %s\n\n",customer[j].getPhoneNo());
					
				System.out.println("Account activities");
				System.out.printf("====================");
				System.out.printf("\nTotal Reservations	: %d",customer[j].getReserveCount());
				System.out.printf("\nTotal Cancelled		: %d\n",customer[j].getCancelCount());
			
				System.out.println("\n-----------------------------------------------");
				System.out.println("|Ticket ID\tReservation Status\t\tPayment ID|");
				System.out.println("-----------------------------------------------");

				for(int b=0;b<customer[j].getReserveCount();b++){
						
					System.out.printf(" %-11s%-24s%s\n",customer[j].getReservation(b).getTicketID() ,customer[j].getReservation(b).getStatus(),customer[j].getReservation(b).getPayment().getPaymentId());
						
				}

				do{
					System.out.printf("\n\nDo you wish to continue search ?(Y/N): ");
					choice=scanner.next().charAt(0);
				}while(checkChar(choice)==false);

				if(Character.toUpperCase(choice)=='Y'){
					do{
						System.out.println("\nSearch");
						System.out.println("=========");
						System.out.println("1. Reservation ID");
						System.out.println("2. Payment ID");
						System.out.println("3. Reservation Status");
						System.out.println("4. Exit");

						System.out.printf("\nEnter your choice(1-4)");
						searchChoice=scanner.nextInt();

						if(searchChoice==1){
						System.out.printf("Enter the Reservation ID:");
						scanner.nextLine();
						reserveID=scanner.nextLine();

						for(int i=0;i<customer[j].getReserveCount();i++){
							if(reserveID.toUpperCase().equals(customer[j].getReservation(i).getTicketID())){
								recordFound=1;

								Payment.generateReceipt(customer[j].getReservation(i), customer[j].getReservation(i).getPayment(), customer[j]);

								for(int a=0;a<customer[j].getReserveCount();a++){
									if(customer[j].getReservation(a).getStatus()=="CANCELLED"){
										for(int b=0;b<customer[j].getCancelCount();b++){
											if(customer[j].getCancellation(b).getReservation().getTicketID()==customer[i].getReservation(a).getTicketID()){
												Cancellation.displayCancellation(customer[i].getReservation(a));
											}
										}
									}
								}
							}
						}
					}
						else if(searchChoice==2){
						System.out.printf("\nEnter the Payment ID:");
						scanner.nextLine();
						paymentID=scanner.nextLine();

						for(int i=0;i<customer[j].getReserveCount();i++){
							if(paymentID.equals(customer[j].getReservation(i).getPayment().getPaymentId())){
								recordFound=1;

								Payment.generateReceipt(customer[j].getReservation(i), customer[j].getReservation(i).getPayment(), customer[j]);

								for(int a=0;a<customer[j].getReserveCount();a++){
									if(customer[j].getReservation(a).getStatus()=="CANCELLED"){
										for(int b=0;b<customer[j].getCancelCount();b++){
											if(customer[j].getCancellation(b).getReservation().getTicketID()==customer[j].getReservation(a).getTicketID()){
													Cancellation.displayCancellation(customer[j].getReservation(a));
											}
										}
									}
								}
							}

						}
					}
						else if(searchChoice==3){

						System.out.println("Reservation Status");
						System.out.println("1. Active");
						System.out.println("2. Completed");
						System.out.println("3. Cancelled");
						System.out.printf("\nEnter your choice(1-3):");
						scanner.nextLine();
						statusChoice=scanner.nextInt();
						if(statusChoice==1){
							for(int i=0;i<customer[j].getReserveCount();i++){
								if(customer[j].getReservation(i).getStatus()=="ACTIVE"){

									recordFound=1;
									Payment.generateReceipt(customer[j].getReservation(i), customer[j].getReservation(i).getPayment(), customer[j]);

								}
							}
						}
						else if(statusChoice==2){
							for(int i=0;i<customer[j].getReserveCount();i++){
								if(customer[j].getReservation(i).getStatus()=="COMPLETE"){

									recordFound=1;									
									Payment.generateReceipt(customer[j].getReservation(i), customer[j].getReservation(i).getPayment(), customer[j]);

								}

							}
						}
						else{
							for(int i=0;i<customer[j].getReserveCount();i++){
								if(customer[j].getReservation(i).getStatus()=="CANCELLED"){
									
									recordFound=1;									
									Payment.generateReceipt(customer[j].getReservation(i), customer[j].getReservation(i).getPayment(), customer[j]);

									for(int b=0;b<customer[j].getCancelCount();b++){
										if(customer[j].getCancellation(b).getReservation().getTicketID()==customer[j].getReservation(i).getTicketID()){
											Cancellation.displayCancellation(customer[i].getReservation(i));
										}
									}
								}
								
							}
						}
						scanner.nextLine();
					}
						else{
							break;
						}
						if(recordFound==0){
						
							System.out.println("Record Not Found!");
							System.out.printf("Do you want to search again? (Y/N):");
							repeat=scanner.next().charAt(0);
						}
					}while(Character.toUpperCase(repeat)=='Y');
				}
				break;
			}

		}
		if(custFound==0){
			System.out.println("Record not found!");
		}
	}

	static LocalDate stringToDate(String date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
		LocalDate reservationDate=LocalDate.parse(date,formatter);

		return reservationDate;
	}

	public static boolean checkIsDigit(String input){
		boolean result=true;

		for(int i=0;i<input.length();i++){
		if(Character.isDigit(input.charAt(i))==false){
			System.out.println("Error, your input should contain number only!");
			result = false;
			break;
		}
		else{
			result= true;
		}
	}
	return result;

	}

	public static boolean checkLength(String input,int max,int min){

		if(input.length()>max||input.length()<min){
			System.out.printf("Invalid length!\n");
			return false;
		}
		else{
			return true;
		}
	}

	public static boolean checkInt(int input,int min,int max){
		if(input > max || input < min){
			System.out.printf("Invalid option (Valid option: %d - %d)\n", min, max);
			return false;
		}

		else{
			return true;
		}
	}

	public static boolean checkChar(char input){
		if(Character.toUpperCase(input) != 'Y' && Character.toUpperCase(input) != 'N'){
			System.out.println("Invalid option (Valid option: Y / N)");
			return false;
		}

		else{
			return true;
		}
	}

	public static void updateReservation(Customer[] customer,int custRecordCount){
		for(int i=0;i<custRecordCount;i++){
			for(int j=0;j<customer[i].getReserveCount();j++){
				if(customer[i].getReservation(j).getDepartDate().compareTo(LocalDate.now())<0){
					customer[i].getReservation(j).setStatus("COMPLETE");

				}
			}
		}
	}
}