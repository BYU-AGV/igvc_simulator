package com.byu_igvc.core.scene.model;

import org.lwjgl.assimp.*;
import org.lwjgl.system.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.assimp.Assimp.*;
import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.system.MemoryUtil.memAddress;
import static org.lwjgl.system.MemoryUtil.memCopy;

public class AssimpModelLoader {
    public AiModel loadModel(String path) {
        AIFileIO fileIo = AIFileIO.create();
        AIFileOpenProcI fileOpenProc = new AIFileOpenProc() {
            public long invoke(long pFileIO, long fileName, long openMode) {
                AIFile aiFile = AIFile.create();
                final ByteBuffer data;
                String fileNameUtf8 = MemoryUtil.memUTF8(fileName);
                try {
                    data = ByteBuffer.wrap(Files.readAllBytes(Paths.get(fileNameUtf8)));
//                    data = ioResourceToByteBuffer(fileNameUtf8, 8192);
                } catch (IOException e) {
                    throw new RuntimeException("Could not open file: " + fileNameUtf8);
                }
                AIFileReadProcI fileReadProc = new AIFileReadProc() {
                    public long invoke(long pFile, long pBuffer, long size, long count) {
                        long max = Math.min(data.remaining(), size * count);
                        memCopy(memAddress(data) + data.position(), pBuffer, max);
                        return max;
                    }
                };
                AIFileSeekI fileSeekProc = new AIFileSeek() {
                    public int invoke(long pFile, long offset, int origin) {
                        if (origin == Assimp.aiOrigin_CUR) {
                            data.position(data.position() + (int) offset);
                        } else if (origin == Assimp.aiOrigin_SET) {
                            data.position((int) offset);
                        } else if (origin == Assimp.aiOrigin_END) {
                            data.position(data.limit() + (int) offset);
                        }
                        return 0;
                    }
                };
                AIFileTellProcI fileTellProc = new AIFileTellProc() {
                    public long invoke(long pFile) {
                        return data.limit();
                    }
                };
                aiFile.ReadProc(fileReadProc);
                aiFile.SeekProc(fileSeekProc);
                aiFile.FileSizeProc(fileTellProc);
                return aiFile.address();
            }
        };
        AIFileCloseProcI fileCloseProc = new AIFileCloseProc() {
            public void invoke(long pFileIO, long pFile) {
                /* Nothing to do */
            }
        };
        fileIo.set(fileOpenProc, fileCloseProc, NULL);
        AIScene scene = aiImportFileEx("org/lwjgl/demo/opengl/assimp/magnet.obj",
                aiProcess_JoinIdenticalVertices | aiProcess_Triangulate, fileIo);
        if (scene == null) {
            throw new IllegalStateException(aiGetErrorString());
        }

        return new AiModel(scene);
    }
}
