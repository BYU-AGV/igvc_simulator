package com.byu_igvc.core.utils;

import glm.vec._3.Vec3;

public class VectorUtil {
    public static Vec3 add(Vec3 a, Vec3 b) {
        return new Vec3(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public static Vec3 cross(Vec3 a, Vec3 b) {
        return new Vec3((a.y * b.z) - (b.y * a.z), ((a.z * b.x) - (b.z * a.x)), (a.x * b.y) - (b.x * a.y));
    }

    public static double length(Vec3 a) {
        return Math.sqrt(Math.pow(a.x, 2) + Math.pow(a.y, 2) + Math.pow(a.z, 2));
    }

    public static Vec3 normalize(Vec3 a) {
        double length = length(a);
        return new Vec3(a.x / length, a.y / length, a.z / length);
    }
}
