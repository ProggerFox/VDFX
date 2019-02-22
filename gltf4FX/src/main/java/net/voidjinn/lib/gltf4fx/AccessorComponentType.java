/*
Copyright (c) 2018-2019, FalcoTheBold
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.voidjinn.lib.gltf4fx;

/**
 *
 * @author FalcoTheBold
 */
public enum AccessorComponentType {

  BYTE(5120, 1, ParseType.BYTE),
  UNSIGNED_BYTE(5121, 1, ParseType.BYTE),
  SHORT(5122, 2, ParseType.SHORT),
  UNSIGNED_SHORT(5123, 2, ParseType.SHORT),
  UNSIGNED_INT(5125, 4, ParseType.INTEGER),
  GL_FLOAT(5126, 4, ParseType.FLOAT);

  private final int id;
  private final int size;
  private final ParseType type;

  private AccessorComponentType(int id, int size, ParseType type) {
    this.id = id;
    this.size = size;
    this.type = type;
  }

  public int getId() {
    return id;
  }

  public int getSize() {
    return size;
  }

  public ParseType getType() {
    return type;
  }

  public static AccessorComponentType getById(int id) {
    for (AccessorComponentType value : values()) {
      if (value.getId() == id) {
        return value;
      }
    }
    return null;
  }

  public enum ParseType {
    BYTE,
    SHORT,
    INTEGER,
    FLOAT
  }
}
