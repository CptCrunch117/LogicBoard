This is a java console implementation of the  project.
It's purpose is for proof of concept. 
Once completed it will be implemented as an application using GUI. 
This project focuses on the data structure to handle creation,edits, and saves of a logicBoard project.
All source code has been designed and implemented by Kyle Ferguson (Singularity Systems). 
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

As of now a clean up and error catching process is being done on the data structure.
Once done Logic blocking, object saving and cloning will be implemented. This will allow 
for saving of LogicBoards (blocks) and when deserialized multiple copies can be made so you can
have mulitple logic blocks with the same logic from the saved version althroughout any other logic board.

