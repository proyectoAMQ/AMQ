/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.chilkatsoft;

public class CkStringTable {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected CkStringTable(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CkStringTable obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        chilkatJNI.delete_CkStringTable(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public CkStringTable() {
    this(chilkatJNI.new_CkStringTable(), true);
  }

  public void LastErrorXml(CkString str) {
    chilkatJNI.CkStringTable_LastErrorXml(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public void LastErrorHtml(CkString str) {
    chilkatJNI.CkStringTable_LastErrorHtml(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public void LastErrorText(CkString str) {
    chilkatJNI.CkStringTable_LastErrorText(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public int get_Count() {
    return chilkatJNI.CkStringTable_get_Count(swigCPtr, this);
  }

  public void get_DebugLogFilePath(CkString str) {
    chilkatJNI.CkStringTable_get_DebugLogFilePath(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String debugLogFilePath() {
    return chilkatJNI.CkStringTable_debugLogFilePath(swigCPtr, this);
  }

  public void put_DebugLogFilePath(String newVal) {
    chilkatJNI.CkStringTable_put_DebugLogFilePath(swigCPtr, this, newVal);
  }

  public void get_LastErrorHtml(CkString str) {
    chilkatJNI.CkStringTable_get_LastErrorHtml(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String lastErrorHtml() {
    return chilkatJNI.CkStringTable_lastErrorHtml(swigCPtr, this);
  }

  public void get_LastErrorText(CkString str) {
    chilkatJNI.CkStringTable_get_LastErrorText(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String lastErrorText() {
    return chilkatJNI.CkStringTable_lastErrorText(swigCPtr, this);
  }

  public void get_LastErrorXml(CkString str) {
    chilkatJNI.CkStringTable_get_LastErrorXml(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String lastErrorXml() {
    return chilkatJNI.CkStringTable_lastErrorXml(swigCPtr, this);
  }

  public boolean get_LastMethodSuccess() {
    return chilkatJNI.CkStringTable_get_LastMethodSuccess(swigCPtr, this);
  }

  public void put_LastMethodSuccess(boolean newVal) {
    chilkatJNI.CkStringTable_put_LastMethodSuccess(swigCPtr, this, newVal);
  }

  public boolean get_VerboseLogging() {
    return chilkatJNI.CkStringTable_get_VerboseLogging(swigCPtr, this);
  }

  public void put_VerboseLogging(boolean newVal) {
    chilkatJNI.CkStringTable_put_VerboseLogging(swigCPtr, this, newVal);
  }

  public void get_Version(CkString str) {
    chilkatJNI.CkStringTable_get_Version(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String version() {
    return chilkatJNI.CkStringTable_version(swigCPtr, this);
  }

  public boolean Append(String value) {
    return chilkatJNI.CkStringTable_Append(swigCPtr, this, value);
  }

  public boolean AppendFromFile(int maxLineLen, String charset, String path) {
    return chilkatJNI.CkStringTable_AppendFromFile(swigCPtr, this, maxLineLen, charset, path);
  }

  public boolean AppendFromSb(CkStringBuilder sb) {
    return chilkatJNI.CkStringTable_AppendFromSb(swigCPtr, this, CkStringBuilder.getCPtr(sb), sb);
  }

  public void Clear() {
    chilkatJNI.CkStringTable_Clear(swigCPtr, this);
  }

  public int FindSubstring(int startIndex, String substr, boolean caseSensitive) {
    return chilkatJNI.CkStringTable_FindSubstring(swigCPtr, this, startIndex, substr, caseSensitive);
  }

  public boolean GetStrings(int startIdx, int count, boolean crlf, CkString outStr) {
    return chilkatJNI.CkStringTable_GetStrings(swigCPtr, this, startIdx, count, crlf, CkString.getCPtr(outStr), outStr);
  }

  public String getStrings(int startIdx, int count, boolean crlf) {
    return chilkatJNI.CkStringTable_getStrings(swigCPtr, this, startIdx, count, crlf);
  }

  public String strings(int startIdx, int count, boolean crlf) {
    return chilkatJNI.CkStringTable_strings(swigCPtr, this, startIdx, count, crlf);
  }

  public int IntAt(int index) {
    return chilkatJNI.CkStringTable_IntAt(swigCPtr, this, index);
  }

  public boolean SaveLastError(String path) {
    return chilkatJNI.CkStringTable_SaveLastError(swigCPtr, this, path);
  }

  public boolean SaveToFile(String charset, boolean bCrlf, String path) {
    return chilkatJNI.CkStringTable_SaveToFile(swigCPtr, this, charset, bCrlf, path);
  }

  public boolean Sort(boolean ascending, boolean caseSensitive) {
    return chilkatJNI.CkStringTable_Sort(swigCPtr, this, ascending, caseSensitive);
  }

  public boolean SplitAndAppend(String inStr, String delimiterChar, boolean exceptDoubleQuoted, boolean exceptEscaped) {
    return chilkatJNI.CkStringTable_SplitAndAppend(swigCPtr, this, inStr, delimiterChar, exceptDoubleQuoted, exceptEscaped);
  }

  public boolean StringAt(int index, CkString outStr) {
    return chilkatJNI.CkStringTable_StringAt(swigCPtr, this, index, CkString.getCPtr(outStr), outStr);
  }

  public String stringAt(int index) {
    return chilkatJNI.CkStringTable_stringAt(swigCPtr, this, index);
  }

}
