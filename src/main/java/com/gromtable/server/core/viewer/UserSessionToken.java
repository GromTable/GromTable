package com.gromtable.server.core.viewer;

import java.nio.ByteBuffer;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.data.RowKey;
import com.gromtable.server.core.environment.Environment;

public class UserSessionToken {
  public static final int DB_ID_SIZE = 2;
  public static final int TOKEN_SIZE = 16;
  public static final int SIZE = DB_ID_SIZE + TOKEN_SIZE;


  private byte[] data;

  private UserSessionToken(byte[] data) {
    this.data = data;
  }

  public String getCookieData() {
    return Hex.encodeHexString(data);
  }

  public Key getKeyData() {
    return new RowKey(data);
  }

  public static UserSessionToken create(Id userId, Environment environment) {
    ByteBuffer buffer = ByteBuffer.allocate(SIZE);
    buffer.putShort((short) userId.getDbId());
    byte[] token = new byte[TOKEN_SIZE];
    environment.getRandom().nextBytes(token);
    buffer.put(token);
    return new UserSessionToken(buffer.array());
  }

  public static UserSessionToken createFromCookieToken(String cookieToken) {
    byte[] data;
    try {
      data = Hex.decodeHex(cookieToken.toCharArray());
    } catch (DecoderException e) {
      return null;
    }
    return new UserSessionToken(data);
  }
}
