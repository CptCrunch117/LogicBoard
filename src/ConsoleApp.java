import logicboard.LogicBoard;
        import logicboard.boardbuilder.BoardTreeBuilder;
        import logicboard.gates.Gate;
        import sun.rmi.runtime.Log;

        import java.io.File;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.Scanner;

/**
 * Created by CptAmerica on 7/8/15.
 */
public class ConsoleApp {
    
    public static int lines = 0;
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        ConsoleApp app = new ConsoleApp();

        //Home functions:
        //browse LogicBoard Libraries: standard Library and User Library (can delete files but only in user_lib).
        //Create new LogicBoard
        //boolean expressions
        //Help & Guides
        Scanner scan = new Scanner(System.in);
        String input;
        boolean HomeLoop = true;
        do{
            homePrompt();
            input = scan.nextLine();
            app.closeAction();

            //HomeLoop IF stucture:

            if (input.equalsIgnoreCase("1")) {
                boolean libBrowseLoop = true;
                do{
                    browseLibPrompt();
                    input = scan.nextLine();
                    app.closeAction();

                    //Browse Loop If structure:
                    if (input.equalsIgnoreCase("1")) {
                        boolean standLibBrowseLoop = true;
                        do{
                            File[] files = stndardLibFilesPrompt();
                            input = scan.nextLine();
                            libBrowseLoop = true;
                            if(input.equalsIgnoreCase("b") || input.equalsIgnoreCase("back")){
                                standLibBrowseLoop = false;
                            }
                            else{
                                int fileIndex = 0;
                                try{
                                    fileIndex = Integer.valueOf(input);
                                    fileIndex -= 1;
                                }catch(Exception e){
                                    app.closeAction();
                                    continue;
                                }
                                boolean viewFileLoop = true;
                                do {
                                    app.closeAction();
                                    println("<<grab and view file options here>>");
                                    LogicBoard boardViewer = LogicBoard.getBoard(LogicBoard.STANDARD_LIB, files[fileIndex].getName());
                                    fileOptionsPrompt(files[fileIndex].getName());
                                    input = scan.nextLine();
                                    if (input.equalsIgnoreCase("1")) {
                                        println(boardViewer.generateTruthTable());
                                        viewFileLoop = true;
                                    } else if (input.equalsIgnoreCase("2")) {
                                        int gateIndex = 0;
                                        println("------------------------------------------");
                                        for(Gate g : boardViewer.getAllGates()){
                                            println(gateIndex+". "+g.getGateID());
                                            println();
                                        }
                                        println("------------------------------------------");
                                        viewFileLoop = true;
                                    } else if (input.equalsIgnoreCase("3")) {
                                        boardViewer.printGates();
                                        viewFileLoop = true;
                                    } else if (input.equalsIgnoreCase("4")) {

                                        for(Gate g : boardViewer.getSysOut()){
                                            System.out.println(g.getGateID()+" = "+ g.getExpression());
                                        }
                                        viewFileLoop = true;
                                    } else if (input.equalsIgnoreCase("b") || input.equalsIgnoreCase("back")) {
                                        viewFileLoop = false;
                                    }
                                    //handle the number entered
                                }while(viewFileLoop);//END VIEW FILE LOOP
                            }//END ElSE (if back cmd was not entered else)
                        }while(standLibBrowseLoop);//END STANDARD LIBRARY BROWSE LOOP
                    } else if (input.equalsIgnoreCase("2")) {
                        //userLibFilesPrompt();
                        app.browseLib(LogicBoard.USER_LIB);
                        libBrowseLoop = true;
                    } else if (input.equalsIgnoreCase("b") || input.equalsIgnoreCase("back")) {
                        libBrowseLoop = false;
                    }
                }while(libBrowseLoop);
            }//END BROWSE LIB FUNCTION


            else if (input.equalsIgnoreCase("2")) {
                createBoardInputsPrompt();
                ArrayList<String> inputs = new ArrayList<String>();
                do {
                    print("> ");
                    input = scan.nextLine();
                    if (!input.equalsIgnoreCase("@@")) {
                        inputs.add(input);
                    }
                } while (!input.equalsIgnoreCase("@@"));
                println("------------------------------------------------------------------");
                int size = inputs.size();
                String[] inp = new String[size];
                for (int i = 0; i < size; i++) {
                    inp[i] = inputs.get(i);
                }
                createBoardNamePrompt();
                input = scan.nextLine();
                LogicBoard tempBoard = new LogicBoard(inp, input);
                tempBoard.saveBoard(LogicBoard.USER_LIB);
                println(tempBoard.getGateID() + " Board has been created and save to the user library!");
                app.editMode(tempBoard);

            }//END CREATE LOGICBOARD FUNCTION
            else if (input.equalsIgnoreCase("3")) {

            }//END HELP & GUIDES FUNCTION
            else if (input.equalsIgnoreCase("4")) {
                app.closeApp();
            }//END CLOSE APP FUNCTION
            app.closeAction();

        }while(HomeLoop);//END OF HOME-LOOP

    }//END MAIN METHOD



    public static void homePrompt(){
        println("--------------------------------------------------------------------------------");
        println("Welcome to the LogicBoard gate simulator. This is a console\n" +
                "implementation of the application for testing purposes only.");
        println("Select a function of the application to use by entering the option's\n" +
                "corresponding number (to the left):");
        println("1. Browse Libraries");
        println("2. Create new LogicBoard");
        println("3. Help & Guides");
        println("4. Quit");
        println("--------------------------------------------------------------------------------");
        print("> ");
    }

    /**
     * Method that returns a list of file objects that reside in the standard lib folder.
     * @return
     */
    public static File[] getStandardLibrary(){
        File standir = new File(LogicBoard.STANDARD_LIB);
        File[] files = standir.listFiles();
        return files;
    }

    /**
     * Method that returns a list of file objects that reside in the user lib folder.
     * @return
     */
    public static File[] getUserLibrary(){
        File usedir = new File(LogicBoard.USER_LIB);
        File[] files = usedir.listFiles();
        return files;
    }

    /**
     * Prompts user to select a library to view.
     */
    public static void browseLibPrompt(){
        println("------------------------------------------------------------------");
        println("Enter the number corresponding to the library you wish to browse:");
        println("1. " + LogicBoard.STANDARD_LIB);
        println("2. " + LogicBoard.USER_LIB);
        println("Back?");
        println("------------------------------------------------------------------");
        print("> ");


    }
    /**
     * Prompts user to select a file from the user library to view/edit.
     */
    public static File[] userLibFilesPrompt(){
        File[] userfiles = getUserLibrary();
        println("------------------------------------------------------------------");
        println("Enter ('b' or 'back' to go back) the number corresponding to the file you wish to view/edit:");
        for(int i=0; i < userfiles.length; i++) {
            println((i+1) + ". " + userfiles[i].getName());
        }
        println("Back?");
        println("------------------------------------------------------------------");
        print("> ");
        return userfiles;
    }

    /**
     * Prompts user to select a file from the standard library to view.
     */
    public static File[] stndardLibFilesPrompt(){
        File[] standfiles = getStandardLibrary();
        println("------------------------------------------------------------------");
        println("Enter ('b' or 'back' to go back) the number corresponding to the file you wish to view:");
        for(int i=0; i < standfiles.length; i++) {
            println((i+1) + ". " + standfiles[i].getName());
        }
        println("Back?");
        println("------------------------------------------------------------------");
        print("> ");
        return standfiles;
    }

    public static void createBoardInputsPrompt(){
        println("------------------------------------------------------------------");
        println("Enter the names of the inputs for the new LogicBoard. Enter: @@ to exit:");
    }

    public static void createBoardNamePrompt(){
        println("Enter the name for the new LogicBoard:");
    }

    public static void editBoardPrompt(){
        println("Enter number to the corresponding editor function you wish to use.('b' or 'back' to go back.)");
        println("1. Add boolean Expressions to Board");
        println("2. Add gates to Board");
        println("Back?");
    }

    public static void fileOptionsPrompt(String filename){
        println("------------------------------------------------------------------");
        println("What would you like to see in LogicBoard/Gate: "+filename+"?");
        println("1. TruthTable");
        println("2. List of all Gates");
        println("3. List of Gate Associations");
        println("4. View Boolean expressions");
        println("back?");
        println("------------------------------------------------------------------");
        print("> ");
    }

    public static void editPrompt(){
        println("Enter the number that corresponds with the function you wish to use, enter 'b' or 'back' to exit editmode.\n" +
                "(Don't worry your board will automatically be saved!)");
        println("1. Add Gate");
        println("2. Add boolean expression");
        println("3. View board data");
        println("4. Remove Gate");
        println("5. Replace Gate");
        println("Back?");
        print("> ");
    }

    public static void promptAddGate(){
        println("What would you like to add? (enter 'b' or 'back' to go back.)");
        println("1. Add AND gate");
        println("2. Add OR gate");
        println("3. Add NOT gate");
        println("4. Gate from User Library");
        println("5. Gate from Standard Library");
        println("Back?");
        print("> ");

    }

    public void closeApp(){
        println("Thank you for using the LogicBoard Gate simulator, GoodBye!");
        System.exit(0);
    }

    public void closeAction(){
        println("--------------------------------------------------------------------------------");
        refreshConsole();
    }


    public void browseLib(String lib) throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        String input;
        //Browse Loop If structure:
        boolean LibBrowseLoop = true;
        do{
            File standir = new File(lib);
            File[] files = standir.listFiles();
            println("select a file with it's corresponding number ('b' or 'back' to go back):");
            for(int i=0; i < files.length; i++){
                println((i+1)+". "+files[i].getName());
            }
            println("Back?");
            println("---------------------------------------------------------------------------");
            print("> ");
            input = scan.nextLine();
            if(input.equalsIgnoreCase("b") || input.equalsIgnoreCase("back")){
                LibBrowseLoop = false;
            }
            else{
                int fileIndex = 0;
                try{
                    fileIndex = Integer.valueOf(input);
                    fileIndex -= 1;
                }catch(Exception e){
                    closeAction();
                    continue;
                }
                boolean viewFileLoop = true;
                do {
                    closeAction();
                    LogicBoard boardViewer = LogicBoard.getBoard(lib, files[fileIndex].getName());
                    println("------------------------------------------------------------------");
                    println("What would you like to see in LogicBoard/Gate: "+files[fileIndex].getName()+"?");
                    println("1. TruthTable");
                    println("2. List of all Gates");
                    println("3. List of Gate Associations");
                    println("4. View Boolean expressions");
                    if(lib.equalsIgnoreCase(LogicBoard.USER_LIB) && !boardViewer.getLogicLock()){
                        println("5. Edit Board");
                    }
                    println("back?");
                    println("------------------------------------------------------------------");
                    print("> ");
                    input = scan.nextLine();
                    if (input.equalsIgnoreCase("1")) {
                        println(boardViewer.generateTruthTable());
                        viewFileLoop = true;
                    } else if (input.equalsIgnoreCase("2")) {
                        int gateIndex = 0;
                        println("------------------------------------------");
                        for(Gate g : boardViewer.getAllGates()){
                            println(gateIndex+". "+g.getGateID());
                            println();
                        }
                        println("------------------------------------------");
                        viewFileLoop = true;
                    } else if (input.equalsIgnoreCase("3")) {
                        boardViewer.printGates();
                        viewFileLoop = true;
                    } else if (input.equalsIgnoreCase("4")) {

                        for(Gate g : boardViewer.getSysOut()){
                            System.out.println(g.getGateID()+" = "+ g.getExpression());
                        }
                        viewFileLoop = true;
                    }
                    else if(input.equalsIgnoreCase("5")){
                        if(!boardViewer.getLogicLock()){
                            editMode(boardViewer);
                        }
                    }
                    else if (input.equalsIgnoreCase("b") || input.equalsIgnoreCase("back")) {
                        viewFileLoop = false;
                        LibBrowseLoop = false;
                    }
                    //handle the number entered
                }while(viewFileLoop);//END VIEW FILE LOOP
            }//END ElSE (if back cmd was not entered else)


        }while(LibBrowseLoop);//END STANDARD LIBRARY BROWSE LOOP
        return;
    }


    public void add2InputGate(LogicBoard editBoard, String gateType){
        Scanner scan = new Scanner(System.in);
        boolean isSet = false;
        String in;
        ArrayList<Gate> avaInputs = editBoard.getAllGates();
        int size = avaInputs.size();
        int selection = 0;
        Gate in1 = null;
        Gate in2 = null;
        do{
            if(selection == 0) {
                println("Select the first input for the "+gateType+" gate (enter 'b' or 'back' to go back.)");

            }
            else{
                println("Select the second input for the "+gateType+" gate (enter 'b' or 'back' to go back.)");
            }
            for(int i=0; i < size;i++){
                println((i+1)+". "+avaInputs.get(i).getGateID());
            }
            println("Back?");
            print("> ");
            in = scan.nextLine();
            if(in.equalsIgnoreCase("b") || in.equalsIgnoreCase("back")){
                selection = 5;
            }
            else{
                int index = 0;
                index = Integer.valueOf(in);
                index -= 1;
                if(selection == 0){in1 = avaInputs.get(index);}
                else if(selection == 1){in2 = avaInputs.get(index); isSet = true;}
            }
            selection++;
        }while(selection < 2);

        if(isSet){
            if(gateType == LogicBoard.AND) {
                println("Enter name of AND gate:");
                print("> ");
                in = scan.nextLine();
                editBoard.addGate(LogicBoard.AND, in, in1.getGateID(), in2.getGateID());
                println("AND gate " + in + " has been integrated into the logicboard: " + editBoard.getGateID());
            }
            else if(gateType == LogicBoard.OR){
                println("Enter name of OR gate:");
                print("> ");
                in = scan.nextLine();
                editBoard.addGate(LogicBoard.OR, in, in1.getGateID(), in2.getGateID());
                println("OR gate " + in + " has been integrated into the logicboard: " + editBoard.getGateID());
            }
        }

    }

    public void addGateFromLib(LogicBoard editBoard, String library) throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        String in;

        File[] lib;
        if(library.equalsIgnoreCase(LogicBoard.STANDARD_LIB))
            lib = getStandardLibrary();
        else lib = getUserLibrary();
        ArrayList<File> validBoardList = new ArrayList<>();
        for(File f : lib){
            if(!f.getName().equalsIgnoreCase((editBoard.getGateID()+".ser"))){
                LogicBoard temp = LogicBoard.getBoard(library, f.getName());
                if (temp.getLogicLock()) {
                    validBoardList.add(f);
                }
            }
        }
        boolean selectAndAddLoop = true;
        do {
            println("------------------------------------------------------------------");
            println("Enter ('b' or 'back' to go back) the number corresponding to the file you wish use:");
            for (int i = 0; i < validBoardList.size(); i++) {
                println((i + 1) + ". " + validBoardList.get(i).getName());
            }
            println("Back?");
            println("------------------------------------------------------------------");
            print("> ");
            in = scan.nextLine();

            if (in.equalsIgnoreCase("b") || in.equalsIgnoreCase("back")) {
                selectAndAddLoop = false;
            }
            else{
                int index = 0;
                try{index = Integer.valueOf(in);}catch(Exception e){continue;}
                index -= 1;
                if(!(index >= validBoardList.size())){
                    LogicBoard tempBoard = LogicBoard.getBoard(library,validBoardList.get(index).getName());
                    //Select inputs for board
                    String[] inputs = new String[tempBoard.getInputs().size()];
                    for(int x=0; x < tempBoard.getInputs().size(); x++){
                        ArrayList<Gate> avaInputs = editBoard.getAllGates();
                        int size = avaInputs.size();
                        for(int i=0; i < size;i++){
                            println((i+1)+". "+avaInputs.get(i).getGateID());
                        }
                        println("Back?");
                        println("-----------------------------------------------------------");
                        print("> ");
                        in = scan.nextLine();

                        if(in.equalsIgnoreCase("b") || in.equalsIgnoreCase("back")){

                        }
                        else{
                            int indexx = 0;
                            try{indexx = Integer.valueOf(in);}catch (Exception e){continue;}
                            indexx -= 1;
                            if(!(indexx >= avaInputs.size())){
                                inputs[x] = avaInputs.get(indexx).getGateID();
                            }
                        }
                    }//END Getting inputs
                    println("would you like to rename the gate? (current name: "+tempBoard.getGateID()+")");
                    println("1. yes");
                    println("2. no");
                    in = scan.nextLine();
                    String gateID = "";
                    if(in.equalsIgnoreCase("1")){
                        println("Rename to: ");
                        gateID = scan.nextLine();
                    }
                    else{
                        gateID = tempBoard.getGateID();
                    }

                    editBoard.addGate(tempBoard,gateID,inputs);
                    println("Gate has been added!");
                    selectAndAddLoop = false;
                }
                else continue;
            }

        }while(selectAndAddLoop);
    }

    public void removeGate(LogicBoard editBoard){
        Scanner scan = new Scanner(System.in);
        String input;
        boolean removedGate = false;
        do{
            println("Enter number corresponding to the Gate you wish to remove (enter 'b' or 'back' to go back.):");
            int count = 0;
            for(Gate g : editBoard.getAllGates()){
                println((count+1)+". "+g.getGateID());
                count++;
            }
            println("Back?");
            input = scan.nextLine();

            if(input.equalsIgnoreCase("b") || input.equalsIgnoreCase("back")){
                removedGate = true;
            }
            else{
                int index;
                try{
                    index = Integer.valueOf(input);
                    index-=1;
                    editBoard.removeGate(editBoard.getAllGates().get(index).getGateID());
                    println("Gate: " + editBoard.getAllGates().get(index).getGateID()+" has been removed from LogicBoard: "+editBoard.getGateID());
                }catch(Exception e){
                    removedGate = false;
                }
            }


        }while(!removedGate);

    }

    public void editMode(LogicBoard editBoard) throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        String in;
        boolean editMain = true;
        do{
            editPrompt();
            in = scan.nextLine();

            //Function IF logic:
            if(in.equalsIgnoreCase("1")){
                boolean addGateLoop = true;
                do {
                    promptAddGate();
                    in = scan.nextLine();

                    if(in.equalsIgnoreCase("1")){
                        add2InputGate(editBoard,LogicBoard.AND);
                    }//END OF ADD AND GATE LOGIC
                    else if(in.equalsIgnoreCase("2")){
                        add2InputGate(editBoard, LogicBoard.OR);
                    }//END OF ADD OR GATE LOGIC
                    else if(in.equalsIgnoreCase("3")){
                        ArrayList<Gate> avaInputs = editBoard.getAllGates();
                        int size = avaInputs.size();
                        Gate in1 = null;
                        boolean isSelected = false;
                        do{
                            println("Select the input for the NOT gate (enter 'b' or 'back' to go back.)");
                            for(int i=0; i < size;i++){
                                println((i+1)+". "+avaInputs.get(i).getGateID());
                            }
                            println("Back?");
                            print("> ");
                            in = scan.nextLine();
                            if(in.equalsIgnoreCase("b") || in.equalsIgnoreCase("back")){
                                isSelected = true;
                            }
                            else{
                                int index = 0;
                                try{index = Integer.valueOf(in);}catch (Exception e){continue;}
                                index -= 1;
                                in1 = avaInputs.get(index);
                                isSelected = true;
                            }
                        }while(!isSelected);

                        if(in1 != null){
                            println("Enter name of Not gate:");
                            print("> ");
                            in = scan.nextLine();
                            editBoard.addGate(LogicBoard.NOT, in, in1.getGateID(), null);
                            println("NOT gate " + in + " has been integrated into the logicboard: " + editBoard.getGateID());
                        }

                    }//END OF ADD NOT GATE LOGIC
                    else if(in.equalsIgnoreCase("4")){
                        addGateFromLib(editBoard, LogicBoard.USER_LIB);
                    }//END OF ADD GATE FROM STANDARD LIBRARY LOGIC
                    else if(in.equalsIgnoreCase("5")){
                        addGateFromLib(editBoard, LogicBoard.STANDARD_LIB);
                    }//END OF ADD GATE FROM USER LIBRARY LOGIC
                    else if(in.equalsIgnoreCase("b") || in.equalsIgnoreCase("back")){
                        editBoard.saveBoard(LogicBoard.USER_LIB);
                        addGateLoop = false;
                    }//END OF BACK LOGIC
                }while(addGateLoop);


            }//END ADD GATE LOGIC
            else if(in.equalsIgnoreCase("2")){
                BoardTreeBuilder builder = new BoardTreeBuilder();
                println("Available gate to use for expression (use their exact name in your expression): ");
                for(Gate g : editBoard.getAllGates()){
                    println("\t- "+g.getGateID());
                }

                println("Enter the expression to add Example: ( A + B ) * ( C ' ) (Hint: no paretheses around root operator)");
                String expression = scan.nextLine();
                editBoard = builder.buildTree(expression, editBoard);
                println("Expression: "+expression+" has been added to LogicBoard: "+editBoard.getGateID());
            }//END OF ADD BOOLEAN EXPRESSION LOGIC
            else if(in.equalsIgnoreCase("3")){
                boolean viewFileLoop = true;
                do {
                    closeAction();
                    fileOptionsPrompt(editBoard.getGateID());
                    in = scan.nextLine();
                    if (in.equalsIgnoreCase("1")) {
                        println(editBoard.generateTruthTable());
                        viewFileLoop = true;
                    } else if (in.equalsIgnoreCase("2")) {
                        int gateIndex = 0;
                        println("------------------------------------------");
                        for(Gate g : editBoard.getAllGates()){
                            println(gateIndex+". "+g.getGateID());
                            println();
                        }
                        println("------------------------------------------");
                        viewFileLoop = true;
                    } else if (in.equalsIgnoreCase("3")) {
                        editBoard.printGates();
                        viewFileLoop = true;
                    } else if (in.equalsIgnoreCase("4")) {

                        for(Gate g : editBoard.getSysOut()){
                            System.out.println(g.getGateID()+" = "+ g.getExpression());
                        }
                        viewFileLoop = true;
                    } else if (in.equalsIgnoreCase("b") || in.equalsIgnoreCase("back")) {
                        viewFileLoop = false;
                    }
                    //handle the number entered
                }while(viewFileLoop);//END VIEW FILE LOOP
            }//END OF VIEW BOARD DATA LOGIC
            else if(in.equalsIgnoreCase("4")){
                removeGate(editBoard);
            }//END OF REMOVE GATE LOGIC
            else if(in.equalsIgnoreCase("5")){

            }//END OF REPLACE GATE LOGIC
            else if(in.equalsIgnoreCase("b") || in.equalsIgnoreCase("back")){
                editBoard.saveBoard(LogicBoard.USER_LIB);
                editMain = false;
            }//END OF BACK LOGIC

        }while(editMain);//END OF EDITOR
        return;
    }















    /**
     * Clears console screen, preparing it for next menu screen in Application.
     */
    public void refreshConsole(){
        for(int i=0; i < lines; i++){
            System.out.println();
        }
        lines = 0;
    }


    public static void println(String str){
        System.out.println(str);
        lines++;
    }
    public static void println(){
        System.out.println();
        lines++;
    }
    public static String print(String str){
        System.out.print(str);
        return str;
    }


}
