# jAM - java automatic music transcription

`jAM` is an automatic music transcription application. (audio2midi, audio2abc)

Generates and displays sheet music from a recorded, monophonic melody.

## Abstract

This work deals with the problem of automatic transcription of monophonic audio material.
The proposed pipeline is based on two algorithms to detect the fundamental frequency in 
a time-discrete signal, YIN and MPM, and a note-based onset collector.

This project contains the sources  of the developed prototype, the system was evaluated 
and the results are good. An average score of 79.58% recognition rate was achieved.

Developed in 2011 as part of my [thesis](http://mwager.de/assets/Bachelorarbeit.pdf) at [University of Applied Sciences, Augsburg, Germany](http://hs-augsburg.de)

[http://mwager.de/jam](http://mwager.de/jam)


## Sourcecode

All project-relevant Java sources are located in the package `de.hsa.jam`. (directory `/src/de/hsa/jam`)

### Project Dependencies

* `be/` from the [tarsos project](https://github.com/JorenSix/Tarsos) (contains the pitch detection algorithms YIN & MPM)
* `abc/` and `scanner/` from the [abc4j project](http://code.google.com/p/abc4j) (for generating abcnotation and displaying sheet music)

See `de.hsa.jam.jAM.java`, as some small changes were made to this sources.

### docs

The javadoc html files.


## COPYRIGHT

jAM (c) 2011 - 2013

Michael Wager

Developed at University of Applied Sciences Augsburg, Germany (bachelor thesis)

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published
by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston,
MA 02111-1307 USA.

## -----