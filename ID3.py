import numpy as np
import math

# data on which classification needs to be performed
my_data=np.array([['sunny','hot','high','weak','No'], ['sunny','hot','high','strong','No'], ['overcast','hot','high','weak','Yes'], ['rain','mild','high','weak','Yes'], ['rain','cool','normal','weak','Yes'], ['rain','cool','normal','strong','No'], ['overcast','cool','normal','strong','Yes'], ['sunny','mild','high','weak','No'], ['sunny','cool','normal','weak','Yes'], ['rain','mild','normal','weak','Yes'], ['sunny','mild','normal','strong','Yes'], ['overcast','mild','high','strong','Yes'], ['overcast','hot','normal','weak','Yes'], ['rain','mild','high','strong','No']])

# mapping of column names
column_names = { 0: 'Outlook', 1: 'Temperature', 2: 'Humidity', 3: 'Wind', 4: 'PlayTennis'}


""" 
    function to calculate initial entropy of overall data based only on class   variable
    my_data -  overall data for entropy calculation 
    class_col - column which is basis of classification
"""

def calculateInitialEntropy(my_data, class_col):
    entropyValue = 0
    countValues = {}
    #print class_col
    #print my_data
    
    # store count  of each type of value in dictionary countValues
    for value in my_data[:,class_col]:
        countValues[value] = countValues.get(value, 0) + 1
    # calculate the total number of values
    totalCount = sum(countValues.values())
    
    # loop through the distinct values stored in former stage
    for key in countValues.keys():
        # calculate probability of each distinct value
        prob = (countValues[key] / float(totalCount))
        # log 1 = 0 and  0 * log x both are 0 so no need to calculate for such cases
        if(prob != 1 and prob != 0):
            #calculate and keep on subtracting from the original entropy value
            entropyValue = entropyValue - prob * math.log(prob,2)
    
    # return the calculated value of entropy
    return entropyValue



""" 
    function to information gain for determing best split
    initialEntropy - entropy value obtained prior to splitting and subsetting data
    column - column considered for splitting
    my_data - data including the column under consideration and remaining attributes  
    class_col - column which is basis of classification
"""

def calculateInformationGain(initialEntropy, column, my_data, class_col):
    
    # store count of each type of value in dictionary countValues
    countValues = {}
    for value in my_data[:,column]:
        countValues[value] = countValues.get(value, 0) + 1
    # calculate the total number of values
    totalCount = sum(countValues.values())
    
    # now we store count of each type of above value based on classes it belongs to
    countClassValues = {}
    # loop through each distinct type of value in the column
    for key in countValues.keys():
        countClassValues[key] = {}
        # loop through all the rows of data to check which rows contain the considered value
        for data_row in my_data:
            if(set([key]).issubset(set(data_row))):
                #print key
                # Increement the count of that specific value for the particular class
                countClassValues[key][data_row[class_col]] = countClassValues[key].get(data_row[class_col], 0) + 1
    
    # intialise entropy value after splitting to 0
    newEntropy = 0
    
    # loop through all the values
    for key in countValues.keys():
        # calculate probability of the value in the overall data
        prob = (countValues[key] / float(totalCount))
        
        subEntropy = 0
        for classkey in countClassValues[key]:
            # calculate probability of the above value being in a particular class
            subProb = (countClassValues[key][classkey] / float(countValues[key]))
            # log 1 = 0 and  0 * log x both are 0 so no need to calculate for such cases
            if(subProb != 0 and subProb != 1):
                # calculate and keep on subtracting from the original entropy value
                subEntropy = subEntropy - subProb * math.log(subProb,2)
        
        # finally obtain the total value of entropy after splitting
        newEntropy  = newEntropy - (prob * subEntropy)
    
    #print countValues, totalCount, countClassValues, newEntropy
    #print str(initialEntropy+newEntropy)
    
    # return information gain on splitting
    return (initialEntropy + newEntropy)



"""
    function to determine whether a leaf node has been reached
    my_data - data under consideration
    class_col - column which is basis of classification
"""
def stoppingCriteria(my_data, class_col):
    # form set of all values in column
    s = set(my_data[:, class_col])
    
    # if all the values are same i.e. all belong to the same class then the stopping criteria is met otherwise not
    if(len(s) == 1):
        return True
    else:
        return False



"""
    function to return the class to which all values belong when the stopping criteria is met
    my_data - data under consideration
    class_col - column which is basis of classification
"""
def classify(my_data, class_col):
    return my_data[0,class_col]




"""
    function to creat tree for classification based on ID3 algorithm
    initialEntropy - entropy value prior to splitting
    my_data - data under consideration
    class_col - column which is basis of classification
    start_col - starting column from which data needs to be considered
    itera - present number of interation during recurssion
"""

def ID3(initialEntropy, my_data, start_col, class_col, itera):
    # intialise values of column and gain which will then store the values for attribute with best split
    max_col = 0
    max_gain = 0
    
    # loop through all attributes of data
    for i in range(start_col, my_data.shape[1]):
        # we do not consider the classification column
        if(i == class_col):
            continue
        
        # calculate Information Gain for each of the attribute
        temp_gain = calculateInformationGain(initialEntropy, i, my_data, class_col)
        # display progress
        print "iter " + str(itera) + " column " + column_names[i+itera] + " : " + str(temp_gain)
        # keep on updating the attribute for finding the best split
        if(temp_gain > max_gain):
            max_gain = temp_gain
            max_col = i
    
    # display the chosen column
    print "iter " + str(itera) + " : " + "Chosen column is " + column_names[max_col+itera]
    #print max_col, max_gain, my_data[1, max_col]
    
    #print my_data
    
    # intialise a dictionary for storing subsets based on chosen attribute 
    subsets = {}
    # initialise counter for traversing through all rows in the data under consideration
    counter = 0
    # remove the chosen column and prepare data for further classification
    temp_data = np.delete(my_data, max_col, axis=1)
    
    #print temp_data
    
    # loop through values in chosen column and assign subsets for specific values
    for value in my_data[:, max_col]:
        subsets.setdefault(value, []).append(temp_data[counter].tolist())
        counter = counter + 1

    #print subsets
    #print my_data
    
    # loop through each value in the chosen column
    for key in subsets.keys():
        print "iter " + str(itera) + " subsetting on : " + key
        # calculate the initial entropy associated with the current value of chosen column
        subEntropy = calculateInitialEntropy(np.array(subsets[key]), class_col-1)
        #print "iter " + str(itera) + " sub " + str(subEntropy)
        
        # check whether the stopping criteria is met. If yes we do not need to go further by splitting.
        if(stoppingCriteria(np.array(subsets[key]), class_col-1)):
            print "Returning after chosing column: " + key + " due to classification as: " + str(classify(np.array(subsets[key]), class_col-1))
        else:
            ID3(subEntropy, np.array(subsets[key]), 0, class_col-1, itera+1)
        

# the classification column is at index 4 right now
class_col = 4
# calculate overall entropy of data
overallEntropy = calculateInitialEntropy(my_data, class_col)
# start classification using ID3
ID3(overallEntropy, my_data, 0, class_col, 0)

#print stoppingCriteria(np.array([['sunny','hot','high','weak','No'], ['sunny','hot','high','strong','Yes']]), class_col)
#print my_data[[1,3],]
#print my_data
#b = np.delete(my_data, 1, 1)
#print b
#print calculateInformationGain(calculateInitialEntropy(my_data, 4), 0, my_data, 4)
#print my_data.shape[1]


#print (set(['sunny','hot']).issubset(set(my_data[1])))