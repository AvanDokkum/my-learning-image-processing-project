V1:

* Pseudocode image sorting on date and copy images to new folder
* 1: locate the images input folder via a GUI
* 2: create a new folder for the output images
* 3: read all images in the folder
* 4: Locate the name(or location) + date data and put it in an object array.
* : sort the array on dates from oldest to newest.
* : copy images into the new folder
* : test if images are copied and sorted on dates

V2:
* Pseudocode image sorting on date and copy images to output folder
* 1: locate the images input folder via a GUI
* 2: create a new folder for the output images
* 3: save all image metadata in an object List (List<ImageMetaData> images)
* 4: decide: 
  * On what data do I want to order the images, and what if that data is not available as EXIF data?

TEST:  

---
* What is the right time of the image being created? (for test JPEG: 20151213_140810.jpg)
1
Date/Time Original=2015:12:13 14:08:10
2
Date/Time=2015:12:13 14:08:10
3
Date/Time Digitized=2015:12:13 14:08:10


* Other date/time formats: (for test JPEG: 20151213_140810.jpg)
4
Attribute Creation Time=2025-03-09T21:00:56.8523489Z

* Other date/time formats: (for other test images in list)
5
File Modified Date=Sun Mar 09 22:00:56 +01:00 2025
6
Modification Time=Tue Feb 11 02:15:05 +01:00 2025
7
Creation Time=Fri Jan 01 00:00:00 +00:00 1904

---
* 4: (problem/SOLVED) What if input folder is a hierarchical structure with images inside?
* 4: (problem) What if there are double images?
* 5: sort images on date
* 6: create new folder inside the output folder corresponding to the dates needed (week/month/year)
* 7: copy images into the new corresponding date-folders
* 8: test if all images are copied
* 9: (Extra) create .json files with image metadata

---

ImageDynamicObject pseudocode:

//Can I add this ImageDynamicObject to a list/array to save it?
//Deduplicating the images by name?
//Saving the image path into the object for later duplication
//Sorting the list on date-image-taken
//Adding the date-image-taken to file name (to sort the images in file explorer automatically)
//Copying image into output folder on month/day scheme

 ---

AI explaining concepts:
The best way to ask is to structure your question like this:
1. **Specify the concept clearly** – e.g., *"Can you explain Java Records?"*
2. **Define the level of depth** – e.g., *"I want a practical explanation suitable for an experienced Java developer."*
3. **Request comparisons (if relevant)** – e.g., *"How do Records differ from regular classes and Lombok?"*
4. **Ask for real-world use cases** – e.g., *"When should I use Records in a backend application?"*
5. **Include coding examples** – e.g., *"Please provide a simple and an advanced example with best practices."*

### Example Question:
*"Can you explain Java Records? I’m an experienced Java developer and want a practical understanding of how they work, how they compare to regular classes and Lombok, and when to use them in backend applications. Please include both a simple and an advanced example with best practices."*
This way, AI gives a **fast, structured, and complete** explanation with examples that fit.
