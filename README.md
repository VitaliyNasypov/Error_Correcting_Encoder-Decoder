<b><h2>Project JetBrains Academy (hyperskill.org): Error Correcting Encoder-Decoder</h2></b><br><br>

<b>About</b><br>
Errors are inevitable both in life and in the digital world. Errors occur here and there and everywhere, and in this project you will not only imitate this process, but also learn how to cope with errors. It is a chance to experience what early developers had to cope with at the dawn of the computer era. Low-level programming is fun and insightful: try it and you’ll see.<br><br>

<b>Stage 1: Symbol-level error emulator</b><br>
In this stage, you should write a program that creates errors in the input text, 1 random error per 3 symbols. An error means that the character is replaced by another random character. For example, “abc” characters can be “*bc” or “a*c” or “ab*”, where * is a random character. You can replace by any character but recommended to use only uppercase and lowercase English letters, spacebar and numbers.
The input contains a single line the text in which you need to make errors. Only one error per 3 symbols!.<br><br>

<b>Stage 2: Symbol-level correction code</b><br>
In this stage, you should write a program that:
   1. Takes a message the user wants it send. The input contains a single message.
   2. Encode the message by tripling all the symbols.
   3. Simulate sending this message via a poor internet connection (in other words, simulate errors).
   4. Decode it back again.
Output the message on every step!<br><br>

<b>Stage 3: Bit-level error emulator</b><br>
In this stage, you should write a program that reads the text the user wants to send from the send.txt, and simulate the sending through a poor internet connection making one-bit errors in every byte of the text. Notice that this text is no longer a string since after manipulations in every byte it may happen to be that some bytes didn't correspond to a specific character in the table because Java does not use ASCII table representation in their String implementation. Java uses UNICODE that happens to match with ASCII in the first 128 symbols, but no further. The String class is too complicated for low-level manipulations so you should use bytes or chars instead.
The received message which contains an error in a single bit in every byte should be saved into received.txt.<br><br>

<b>Stage 4: Bit-level correction code</b><br>
Update the program, so it now takes input and implements three modes: encode, send, decode.
Encode reads in a file (send.txt) and encodes it by writing (to encoded.txt) each bit twice with the last two bits of each byte parity bits. (i.e. 10100110 becomes 11001100 00001111 11000011).
Send uses what we wrote in the last stage to change one bit in each byte as it reads encoded.txt and writes to received.txt.
Decode reads from received.txt and decodes the file while fixing the errors and writes the result to decoded.txt.<br><br>

<b>Stage 5: Hamming error-correction code</b><br>
Change the encoding/decoding algorithms to now use Hamming code[7, 4].
