package com.gromtable.server.core.data;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * DbId - 2 byte Type - 2 bytes Check - 4 bytes Sequence - 8 bytes
 */
public class Id extends RowKey {
  public static final int MIN_DB_ID = 0;
  public static final int MAX_DB_ID = (1 << 16) - 1;
  public static final int MIN_TYPE_ID = 0;
  public static final int MAX_TYPE_ID = (1 << 16) - 1;
  public static final long MIN_SEQUENCE_ID = 0;
  public static final long MAX_SEQUENCE_ID = -1;
  public static final int SIZE = 16;

  protected static final int DB_ID_OFFSET = 0;
  protected static final int TYPE_ID_OFFSET = 2;
  protected static final int HASH_OFFSET = 4;
  private static final int SEQUENCE_ID_OFFSET = 8;

  private static final MessageDigest MESSAGE_DIGEST = initializeMessageDigest();

  protected Id(byte[] data) {
    super(data);
  }

  private static MessageDigest initializeMessageDigest() {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.update(Hex.decodeHex("1234567890abcdef".toCharArray()));
      return messageDigest;
    } catch (NoSuchAlgorithmException e) {
      return null;
    } catch (DecoderException e) {
      return null;
    }
  }

  private static MessageDigest getMessageDigest() {
    try {
      return (MessageDigest) MESSAGE_DIGEST.clone();
    } catch (CloneNotSupportedException e) {
      return null;
    }
  }

  public static Id fromRowData(byte[] data) {
    return new Id(data);
  }

  public static Id fromBase16(String base16) throws DecoderException {
    return new Id(Hex.decodeHex(base16.toCharArray()));
  }

  public static Id genIdForDb(int dbId, int type, long sequenceId) {
    ByteBuffer buffer = ByteBuffer.allocate(16);
    buffer.putShort(DB_ID_OFFSET, (short) dbId);
    buffer.putShort(TYPE_ID_OFFSET, (short) type);
    buffer.putLong(SEQUENCE_ID_OFFSET, sequenceId);
    buffer.putInt(HASH_OFFSET, calculateHash(buffer.array()));
    return new Id(buffer.array());
  }

  public String getStoreString() {
    return Hex.encodeHexString(getRowData());
  }

  public boolean isValid() {
    int storedHash = getHash();
    int calculatedHash = calculateHash(getRowData());
    return storedHash == calculatedHash;
  }

  protected static int calculateHash(byte[] data) {
    byte[] clonedData = data.clone();
    ByteBuffer buffer = ByteBuffer.wrap(clonedData);
    buffer.putInt(HASH_OFFSET, 0);
    MessageDigest messageDigest = getMessageDigest();
    byte[] hash = messageDigest.digest(buffer.array());
    ByteBuffer hashBuffer = ByteBuffer.wrap(hash);
    return hashBuffer.getInt(0);
  }

  private int getHash() {
    ByteBuffer buf = ByteBuffer.wrap(getRowData());
    return buf.getInt(HASH_OFFSET);
  }

  public int getDbId() {
    ByteBuffer buf = ByteBuffer.wrap(getRowData());
    return buf.getShort(DB_ID_OFFSET) & 0xFFFF;
  }

  public int getTypeId() {
    ByteBuffer buf = ByteBuffer.wrap(getRowData());
    return buf.getShort(TYPE_ID_OFFSET) & 0xFFFF;
  }

  public long getSequenceId() {
    ByteBuffer buf = ByteBuffer.wrap(getRowData());
    return buf.getLong(SEQUENCE_ID_OFFSET);
  }
}
