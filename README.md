# BeepBeep
BeepBeep. A library made to it easier to test paths without
a drivetrain. Incredibly cool.
# INFO
* No splines :(
* Some other stuff not implemented :(
* Please ask about issues :)
# DOCS
Here is my incredible documentation! I know its bad :(  
#### Note: Most things RoadRunner are already here
# `LinearOpMode`  
`Drive drive` - The drive fed to you. Has trajectory and
follow trajectory functions.  
`protected void runOpMode() throws InterruptedException, PathContinuityException` - Run your opmode code here.  
# `Simulation`
`public enum FieldType {  
    POWERPLAY  
}`  
`public Simulation(FieldType ft, int size, Class<?>[] opmodes) throws IOException` - Opmodes must be grabbed through Refleciton api. See Driver.java  
`public void start() throws InterruptedException` - Creates new BeepBeep window  
For the rest of everything just read my code.
# More or less
Keep Driver.java and read BackAndForth.java. They pretty much show you what to do.