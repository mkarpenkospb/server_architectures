// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: protocol.proto

package ru.itmo.protocol;

public final class Protocol {
  private Protocol() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface IntegerArrayOrBuilder extends
      // @@protoc_insertion_point(interface_extends:ru.itmo.protocol.IntegerArray)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated int32 array = 1;</code>
     * @return A list containing the array.
     */
    java.util.List<java.lang.Integer> getArrayList();
    /**
     * <code>repeated int32 array = 1;</code>
     * @return The count of array.
     */
    int getArrayCount();
    /**
     * <code>repeated int32 array = 1;</code>
     * @param index The index of the element to return.
     * @return The array at the given index.
     */
    int getArray(int index);
  }
  /**
   * Protobuf type {@code ru.itmo.protocol.IntegerArray}
   */
  public  static final class IntegerArray extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:ru.itmo.protocol.IntegerArray)
      IntegerArrayOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use IntegerArray.newBuilder() to construct.
    private IntegerArray(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private IntegerArray() {
      array_ = emptyIntList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new IntegerArray();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private IntegerArray(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                array_ = newIntList();
                mutable_bitField0_ |= 0x00000001;
              }
              array_.addInt(input.readInt32());
              break;
            }
            case 10: {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              if (!((mutable_bitField0_ & 0x00000001) != 0) && input.getBytesUntilLimit() > 0) {
                array_ = newIntList();
                mutable_bitField0_ |= 0x00000001;
              }
              while (input.getBytesUntilLimit() > 0) {
                array_.addInt(input.readInt32());
              }
              input.popLimit(limit);
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000001) != 0)) {
          array_.makeImmutable(); // C
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ru.itmo.protocol.Protocol.internal_static_ru_itmo_protocol_IntegerArray_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ru.itmo.protocol.Protocol.internal_static_ru_itmo_protocol_IntegerArray_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ru.itmo.protocol.Protocol.IntegerArray.class, ru.itmo.protocol.Protocol.IntegerArray.Builder.class);
    }

    public static final int ARRAY_FIELD_NUMBER = 1;
    private com.google.protobuf.Internal.IntList array_;
    /**
     * <code>repeated int32 array = 1;</code>
     * @return A list containing the array.
     */
    public java.util.List<java.lang.Integer>
        getArrayList() {
      return array_;
    }
    /**
     * <code>repeated int32 array = 1;</code>
     * @return The count of array.
     */
    public int getArrayCount() {
      return array_.size();
    }
    /**
     * <code>repeated int32 array = 1;</code>
     * @param index The index of the element to return.
     * @return The array at the given index.
     */
    public int getArray(int index) {
      return array_.getInt(index);
    }
    private int arrayMemoizedSerializedSize = -1;

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (getArrayList().size() > 0) {
        output.writeUInt32NoTag(10);
        output.writeUInt32NoTag(arrayMemoizedSerializedSize);
      }
      for (int i = 0; i < array_.size(); i++) {
        output.writeInt32NoTag(array_.getInt(i));
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      {
        int dataSize = 0;
        for (int i = 0; i < array_.size(); i++) {
          dataSize += com.google.protobuf.CodedOutputStream
            .computeInt32SizeNoTag(array_.getInt(i));
        }
        size += dataSize;
        if (!getArrayList().isEmpty()) {
          size += 1;
          size += com.google.protobuf.CodedOutputStream
              .computeInt32SizeNoTag(dataSize);
        }
        arrayMemoizedSerializedSize = dataSize;
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof ru.itmo.protocol.Protocol.IntegerArray)) {
        return super.equals(obj);
      }
      ru.itmo.protocol.Protocol.IntegerArray other = (ru.itmo.protocol.Protocol.IntegerArray) obj;

      if (!getArrayList()
          .equals(other.getArrayList())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (getArrayCount() > 0) {
        hash = (37 * hash) + ARRAY_FIELD_NUMBER;
        hash = (53 * hash) + getArrayList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static ru.itmo.protocol.Protocol.IntegerArray parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ru.itmo.protocol.Protocol.IntegerArray parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ru.itmo.protocol.Protocol.IntegerArray parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ru.itmo.protocol.Protocol.IntegerArray parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ru.itmo.protocol.Protocol.IntegerArray parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ru.itmo.protocol.Protocol.IntegerArray parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ru.itmo.protocol.Protocol.IntegerArray parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ru.itmo.protocol.Protocol.IntegerArray parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static ru.itmo.protocol.Protocol.IntegerArray parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static ru.itmo.protocol.Protocol.IntegerArray parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static ru.itmo.protocol.Protocol.IntegerArray parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ru.itmo.protocol.Protocol.IntegerArray parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(ru.itmo.protocol.Protocol.IntegerArray prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code ru.itmo.protocol.IntegerArray}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:ru.itmo.protocol.IntegerArray)
        ru.itmo.protocol.Protocol.IntegerArrayOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return ru.itmo.protocol.Protocol.internal_static_ru_itmo_protocol_IntegerArray_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return ru.itmo.protocol.Protocol.internal_static_ru_itmo_protocol_IntegerArray_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                ru.itmo.protocol.Protocol.IntegerArray.class, ru.itmo.protocol.Protocol.IntegerArray.Builder.class);
      }

      // Construct using ru.itmo.protocol.Protocol.IntegerArray.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        array_ = emptyIntList();
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return ru.itmo.protocol.Protocol.internal_static_ru_itmo_protocol_IntegerArray_descriptor;
      }

      @java.lang.Override
      public ru.itmo.protocol.Protocol.IntegerArray getDefaultInstanceForType() {
        return ru.itmo.protocol.Protocol.IntegerArray.getDefaultInstance();
      }

      @java.lang.Override
      public ru.itmo.protocol.Protocol.IntegerArray build() {
        ru.itmo.protocol.Protocol.IntegerArray result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public ru.itmo.protocol.Protocol.IntegerArray buildPartial() {
        ru.itmo.protocol.Protocol.IntegerArray result = new ru.itmo.protocol.Protocol.IntegerArray(this);
        int from_bitField0_ = bitField0_;
        if (((bitField0_ & 0x00000001) != 0)) {
          array_.makeImmutable();
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.array_ = array_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof ru.itmo.protocol.Protocol.IntegerArray) {
          return mergeFrom((ru.itmo.protocol.Protocol.IntegerArray)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(ru.itmo.protocol.Protocol.IntegerArray other) {
        if (other == ru.itmo.protocol.Protocol.IntegerArray.getDefaultInstance()) return this;
        if (!other.array_.isEmpty()) {
          if (array_.isEmpty()) {
            array_ = other.array_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureArrayIsMutable();
            array_.addAll(other.array_);
          }
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        ru.itmo.protocol.Protocol.IntegerArray parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (ru.itmo.protocol.Protocol.IntegerArray) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private com.google.protobuf.Internal.IntList array_ = emptyIntList();
      private void ensureArrayIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          array_ = mutableCopy(array_);
          bitField0_ |= 0x00000001;
         }
      }
      /**
       * <code>repeated int32 array = 1;</code>
       * @return A list containing the array.
       */
      public java.util.List<java.lang.Integer>
          getArrayList() {
        return ((bitField0_ & 0x00000001) != 0) ?
                 java.util.Collections.unmodifiableList(array_) : array_;
      }
      /**
       * <code>repeated int32 array = 1;</code>
       * @return The count of array.
       */
      public int getArrayCount() {
        return array_.size();
      }
      /**
       * <code>repeated int32 array = 1;</code>
       * @param index The index of the element to return.
       * @return The array at the given index.
       */
      public int getArray(int index) {
        return array_.getInt(index);
      }
      /**
       * <code>repeated int32 array = 1;</code>
       * @param index The index to set the value at.
       * @param value The array to set.
       * @return This builder for chaining.
       */
      public Builder setArray(
          int index, int value) {
        ensureArrayIsMutable();
        array_.setInt(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int32 array = 1;</code>
       * @param value The array to add.
       * @return This builder for chaining.
       */
      public Builder addArray(int value) {
        ensureArrayIsMutable();
        array_.addInt(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int32 array = 1;</code>
       * @param values The array to add.
       * @return This builder for chaining.
       */
      public Builder addAllArray(
          java.lang.Iterable<? extends java.lang.Integer> values) {
        ensureArrayIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, array_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int32 array = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearArray() {
        array_ = emptyIntList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:ru.itmo.protocol.IntegerArray)
    }

    // @@protoc_insertion_point(class_scope:ru.itmo.protocol.IntegerArray)
    private static final ru.itmo.protocol.Protocol.IntegerArray DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new ru.itmo.protocol.Protocol.IntegerArray();
    }

    public static ru.itmo.protocol.Protocol.IntegerArray getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<IntegerArray>
        PARSER = new com.google.protobuf.AbstractParser<IntegerArray>() {
      @java.lang.Override
      public IntegerArray parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new IntegerArray(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<IntegerArray> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<IntegerArray> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public ru.itmo.protocol.Protocol.IntegerArray getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ru_itmo_protocol_IntegerArray_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ru_itmo_protocol_IntegerArray_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016protocol.proto\022\020ru.itmo.protocol\"\035\n\014In" +
      "tegerArray\022\r\n\005array\030\001 \003(\005b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_ru_itmo_protocol_IntegerArray_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ru_itmo_protocol_IntegerArray_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ru_itmo_protocol_IntegerArray_descriptor,
        new java.lang.String[] { "Array", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
