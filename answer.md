# Problem Description

I have to read input file and depending on command line arguments
I will either have to encode or decode the input stream into or from
Base64 and output the stream into a file.

# Catches
* Java char is 2 bytes in stead of C/C++'s 1 byte chars. So, we have
to strictly deal with. This brings the possibility of having support
for unicode. And there are input file with unicodes.
* If both `-e` and `-d` is present in the argument, we will have to
encode. If none present, we will encode as well.
* Catches in the algorithm for encoding and decoding.
    * We may have to append `=` to make the string length divisible
    by 4
    * Be careful about appending multiple 0 bytes while decoding
    which might not be visible but may generate different MD-5
    hashing value at file level.

# Assumption stated in the problem
* File name will be valid and readable
* Input for Base64 decoding will be valid

# Solution

* For solving first catch we completely deal at bytes level. We read
as bytes and encode/decode as bytes and output to the file as bytes.
* The rest of the problems are coding challenges.

# Project Structure
## `util`

The objective of this package is to contains the classes which 
provides utility support needed for this project. Utility classes
mostly provides a set of static method that does some generic stuff.
We have 2 utility classes.

#### `Base64`
This has two static methods:
* `byte[] encode(byte[])` : This takes a byte array as input and 
returns an encoded byte array as output. This method in used for
encoding a byte array into Base64.
* `byte[] decode(byte[])` : This takes a byte array as input and 
returns a decoded byte array as output. This method in used for
decoding a byte array from Base64.

#### `FileUtil`
This has two static methods:
* `byte[] readFile(String)` : *throws **IOException*** This 
takes a file name as input and returns the contents of the file 
as byte array as output. This method is used for reading the 
contents of input array as file.
* `void writeFile(String, byte[])` : *throws **IOException***This
takes a file name and a byte array to be written on the file as
input and returns nothing. This method is used for writing 
contents to file.

## `App.java`

Contains the `main(String[] args)` method. Basically a switch case
handles `-e` and `-d` situation. And also inits the file names.
Then it uses the `FileUtil.readFile(inputFileName)` to read the 
contents of the file into byte array and then that is passed into
`Base64.encode(byte[])` for encoding or `Base64.decode(byte[])`
for decoding based on environment arguments. And the output steam
then is written by `FileUtil.writeFile(outputStream, inputFileName)`
to a file.

### Authors

* **Sheikh Shakib Ahmed** - *Designing solution and Implementation* - 
[magurmach](https://github.com/magurmach)