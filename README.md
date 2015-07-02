# LogicBoard
This is a java console implementation of the LogicBoard project.
It's purpose is for proof of concept. 
Once completed it will be implemented as an application using GUI. 
This project focuses on the data structure to handle creation,edits, and saves of a logicBoard project.
All source code has been designed and implemented by Kyle Ferguson (Brogrammer Studios). 
This is kind of a private project


Sample of Console
Console Output of OneBitFullAdder class:
<table>
<th>One bit full adder</th>
    <tr>
        <td>A</td>
        <td>B</td>
        <td>Cin</td>
        <td>|</td>
        <td>Cout</td>
        <td>Sum</td>
    </tr>
    <tr>
        <td>0</td>
        <td>0</td>
        <td>0</td>
        <td>  </td>
        <td>0</td>
        <td>0</td>
    </tr>
        <td>0</td>
        <td>0</td>
        <td>1</td>
        <td>  </td>
        <td>0</td>
        <td>1</td>
    </tr>
        <td>0</td>
        <td>1</td>
        <td>0</td>
        <td>  </td>
        <td>0</td>
        <td>1</td>
    </tr>
        <td>0</td>
        <td>1</td>
        <td>1</td>
        <td>  </td>
        <td>1</td>
        <td>0</td>
    </tr>
        <td>1</td>
        <td>0</td>
        <td>0</td>
        <td>  </td>
        <td>0</td>
        <td>1</td>
    </tr>
        <td>1</td>
        <td>0</td>
        <td>1</td>
        <td>  </td>
        <td>1</td>
        <td>0</td>
    </tr>
        <td>1</td>
        <td>1</td>
        <td>0</td>
        <td>  </td>
        <td>1</td>
        <td>0</td>
    </tr>
        <td>1</td>
        <td>1</td>
        <td>1</td>
        <td>  </td>
        <td>1</td>
        <td>1</td>
    </tr>
</table>
Process finished with exit code 0

<h2>Update 1</h2>
As of now a clean up and error catching process is being done on the data structure.
Once done Logic blocking, object saving and cloning will be implemented. This will allow 
for saving of LogicBoards (blocks) and when deserialized multiple copies can be made so you can
have mulitple logic blocks with the same logic from the saved version althroughout any other logic board.

<h2>Update 2</h2>
    Alright as of now new exceptions have been created for certain error occurances in the data structure. 
    Clean up is as always underway. Also Designs for new functions are being made.(hint hint circuit 
    simplification tools :D). However as testing continues I have started designing some much needed
    functions to make building boards more intuitive based on some not so intuitive processes I 
    designed earlier. Logicboards can be saved and cloned. LogicBoards implemented into other logicboards
    now act as a gate object as they now implement the Gate interface. Logicboards also allow you to rename 
    it's system output gates gateID. This allows for more intuitive debugging for whatever user is creating! 
    Might add some addition features for that. This is probably the biggest update... mainly cause I have 
    been lazy and haven't been logging the commits made since update 1. In any case, feel free to look at 
    the code friends. Please notify me before using code, alot of time has been spent on this...also becuase
    it's far from done.
    
<h2>Update 3</h2>
    Alright as of now new exceptions have been created for certain error occurances in the data structure. 
    Clean up is as always underway. Also Designs for new functions are being made.(hint hint circuit 
    simplification tools :D). However as testing continues I have started designing some much needed
    functions to make building boards more intuitive based on some not so intuitive processes I 
    designed earlier. Logicboards can be saved and cloned. LogicBoards implemented into other logicboards
    now act as a gate object as they now implement the Gate interface. Logicboards also allow you to rename 
    it's system output gates gateID. This allows for more intuitive debugging for whatever user is creating! 
    Might add some addition features for that. This is probably the biggest update... mainly cause I have 
    been lazy and haven't been logging the commits made since update 1. In any case, feel free to look at 
    the code friends. Please notify me before using code, alot of time has been spent on this...also becuase
    it's far from done.

