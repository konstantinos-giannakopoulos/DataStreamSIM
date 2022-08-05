
                     DataStreamSIM (DSSIM) simulator

What is it?
-----------

DataStreamSIM (DSSIM) is a simulator for processing data streams. It 
simulates the functionality of two frameworks, i.e. the static model 
and the adaptive model. It is implemented in the Java programming 
language. It has been implemented for the purposes of 
the MSc dissertation with the subject 
"Static Optimisation vs. Dynamic Evaluation for Data Stream Processing".

Documentation
-------------

The documentation available as of the date of this release is 
included in HTML format in the DataStreamSIM/doc directory, and
in the DSSIM/doc directory.

Author and Contact Details
--------------------------

Author: Konstantinos Giannakopoulos
Email #1: K.Giannakopoulos [at] sms [dot] ed [dot] ac [dot] uk
Email #2: kostgiann [at] gmail [dot] com

In the folders...
-----------------

There are included three folders:

The 'DataStreamSIM' folder contains the source code of the simulators,
as it was generated using the Eclipse IDE.

The 'DSSIM' folder includes the jar file of the implementation, along 
with the configuration file, the properties file and the external 
libraries that we incorporated to the implementation. It also 
includes the input folder, which contains the input that the user
should specify for the code to run properly, and the output folder
which contains the results of the execution of the implementation,
i.e log files, statistics and graphs.

The 'datasets' folder includes workloads and execution plans that
are used as input to the system. 

How to run the simulator?
-------------------------

(a) Firstly, the user should configure the system. The configuration 
file, conf.xml, is in the DSSIM/conf directory. Here, the user
specifies if the simulation runs in static or adaptive mode,
real-time or probabilistic-time policy. If the user wants to 
specify the metrics of the system described below, they should 
configure the system in probabilistic-time policy.

(b) Secondly, the user should specify the properties of the system.
The properties file, datastreamsim.properties, is in the DSSIM 
directory.

The user should choose one of the two available parameters to tune
the simulator. In the case that the parameter is ArrivalRate, also
values should be specified for the ArrivalRate, and the ArrivalRateStep. The 
AvgRoutingCost parameter should be set to zero (0.0).

In the case the parameter is AvgRoutingCost, also values should be
specified for the AvgRoutingCost and the AvgRoutingCostStep. The 
ArrivalRate should be set to a non-zero value. 

In the case that the user wants to compare the execution of the 
static and the adaptive model, they should run the system twice by 
modifying the configuration file. In the properties file, during the
first execution they should delete the graphs and files from 
previous execution: EmptyDir = y, while during the second execution
the previously generated graphs should not be deleted: EmptyDir = n.

(c) Thirdly, the user should specify the input of the simulator.
They should use one of the datasets in the datasets directory. Just
copy the txt files of one of the datasets to the DSSIM/input 
directory.

(d) Run the application.
Run the jar file of the application, i.e. DataStreamSIM.jar
that is located in the DSSIM folder, using the command:
       java -Xmx2048m -jar DataStreamSIM.jar
In the cases of heavy workload, it is recommended to use:
       java -Xms2048m -Xmx3072m -jar DataStreamSIM.jar 

(e) Check the output.
The results of the application are included in DSSIM/output directory,

