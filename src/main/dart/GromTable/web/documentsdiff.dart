library documentsdiff;

import 'package:suffixarray/suffixarray.dart';
import 'dart:math';

class DiffPart {
  static const SAME = 'same';
  static const ADD = 'add';
  static const REMOVE = 'remove';
  static const ADD_FROM = 'add_from';
  static const REMOVE_FROM = 'remove_from';
  String text;
  String command;
  DiffPart(this.text, this.command);
}

class SamePart {
  int length;
  int fromInd;
  int toInd;
  SamePart(this.length, this.fromInd, this.toInd);
}

class DocumentsDiff {
  String fromText;
  String toText;
  int minBlockLength;
 
  
  DocumentsDiff(this.fromText, this.toText, this.minBlockLength) {
  }
  
  List<int> getLCP(String text, SuffixArray suffixArray) {
    List<int> sortedSuffixes = suffixArray.getSortedSuffixes();
    if (sortedSuffixes.length < 2) {
      return [];
    }
    List<int> lcp = new List<int>(text.length);
    List<int> inverseSuffixes = new List<int>(sortedSuffixes.length);

    for (int i = 0; i < sortedSuffixes.length; i++) {
      inverseSuffixes[sortedSuffixes[i]] = i;
    }
    int len = 0;
    for (int i = 0; i < text.length; i++) {
      int k = inverseSuffixes[i];
      int j = sortedSuffixes[k-1];
      while (max(i, j) + len < text.length && text[i+len] == text[j+len])
        len++;
      lcp[k-1] = len;
      if (len > 0) {
        len--;
      }     
    }
    return lcp;
  }
  
  List<SamePart> buildSameParts(int fromLength, List<int> suffixArray, List<int> lcp) {
    var sameParts = [];
    for (int i = 0; i < lcp.length; i++) {
      int fromInd = suffixArray[i];
      int toInd = suffixArray[i+1];
      if (fromInd > toInd) {
        int temp = fromInd;
        fromInd = toInd;
        toInd = temp;
      }
      int sameLength = lcp[i];
      int minFromInd = fromLength - fromInd;
      sameLength = min(sameLength, minFromInd);
      if (toInd < fromLength) {
        sameLength = -1;
      }
      if (sameLength >= minBlockLength) {
        sameParts.add(new SamePart(sameLength, fromInd, toInd - fromLength));
      }
    }
    return sameParts;
  }
  
  int freeCount(List<bool> list, int fromInd, int length) {
    for (int i = 0; i < length; i++) {
      if (list[fromInd + i] == true) {
        return i;
      }
    }
    return length;
  }
  
  String getS(List<bool> list) {
    String s = "";
    for (bool x in list) {
      if (x) {
        s += "1";
      } else {
        s += "0";
      }
    }
    return s;
  }
  
  // What is time complexity here? Is it O(n) or O(n^2)?? This is tricky.
  List<SamePart> filterSameParts(List<SamePart> sameParts) {
    List<SamePart> filterdSameParts = [];
    List<bool> fromTextUsed = new List.filled(fromText.length, false);
    List<bool> toTextUsed = new List.filled(toText.length, false);
    for (SamePart samePart in sameParts) {
      int minCount = min(freeCount(fromTextUsed, samePart.fromInd, samePart.length), freeCount(toTextUsed, samePart.toInd, samePart.length));
      samePart.length = minCount;
      if (samePart.length >= minBlockLength) {
        fromTextUsed.fillRange(samePart.fromInd, samePart.fromInd + samePart.length, true);
        toTextUsed.fillRange(samePart.toInd, samePart.toInd + samePart.length, true);
        filterdSameParts.add(samePart);
      }
    }
    return filterdSameParts; 
  }
  
  List<DiffPart> getTextDifference(String fromText, String toText, List<SamePart> sameParts) {
    int maxLength = fromText.length + toText.length + 1;
    sameParts.add(new SamePart(maxLength, fromText.length, toText.length));
    List<SamePart> fromParts = new List<SamePart>.from(sameParts);
    List<SamePart> toParts = new List<SamePart>.from(sameParts);
    fromParts.sort((SamePart x, SamePart y) => x.fromInd - y.fromInd);
    toParts.sort((SamePart x, SamePart y) => x.toInd - y.toInd);
    int fromIndex = 0;
    int toIndex = 0;
    int fromPartIndex = 0;
    int toPartIndex = 0;
    List<DiffPart> editScript = new List<DiffPart>();
    while (fromIndex < fromText.length || toIndex < toText.length) {
      SamePart fromPart = fromParts[fromPartIndex];
      SamePart toPart = toParts[toPartIndex];
      
      SamePart minPart = null;
      String diffText = null;
      String diffPart = null;
      int wasFromIndex = fromIndex;
      int wasToIndex = toIndex;
      if (fromPart == toPart) {
        minPart = fromPart;
        fromPartIndex++;
        toPartIndex++;
        fromIndex = fromPart.fromInd + fromPart.length;
        toIndex = toPart.toInd + toPart.length;
        if (fromPart.length != maxLength) {
          diffText = fromText.substring(fromPart.fromInd, fromPart.fromInd + fromPart.length);
          diffPart = DiffPart.SAME;
        }
      } else if (fromPart.length < toPart.length) {
        minPart = fromPart;
        fromPartIndex++;
        fromIndex = fromPart.fromInd + fromPart.length;
        diffText = fromText.substring(fromPart.fromInd, fromPart.fromInd + fromPart.length);
        diffPart = DiffPart.REMOVE_FROM;
      } else {
        minPart = toPart;
        toPartIndex++;
        toIndex = toPart.toInd + toPart.length;
        diffText = toText.substring(toPart.toInd, toPart.toInd + toPart.length);
        diffPart = DiffPart.ADD_FROM;
      }

      if (diffPart != DiffPart.ADD_FROM && wasFromIndex < minPart.fromInd) {
        editScript.add(new DiffPart(fromText.substring(wasFromIndex, minPart.fromInd), DiffPart.REMOVE));
      }
      if (diffPart != DiffPart.REMOVE_FROM && wasToIndex < minPart.toInd) {
        editScript.add(new DiffPart(toText.substring(wasToIndex, minPart.toInd), DiffPart.ADD));
      }    
      
      if (diffPart != null) {
        editScript.add(new DiffPart(diffText, diffPart));
      }
    }
    return editScript;
  }
  
  /**
   * Ideally this should work in O(n) time.
   * But for now:
   * 1) SuffixArray work in O(n*logn*logn)
   * 2) Sorting work in O(n*logn)
   */
  List<DiffPart> getEditScript() {
    String suffixText = fromText + toText;
    SuffixArray suffixArray = new SuffixArray(suffixText);
    List<int> lcp = getLCP(suffixText, suffixArray);
    List<SamePart> sameParts = buildSameParts(fromText.length, suffixArray.getSortedSuffixes(), lcp);
    sameParts.sort((SamePart x, SamePart y) => y.length - x.length);
    sameParts = filterSameParts(sameParts);
    return getTextDifference(fromText, toText, sameParts);
  }
}