package renderer;

import geometries.Geometries;
import geometries.Intersectable;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import java.util.List;

import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;

/**
 * Testing basic shadows
 *
 * @author Dan
 */
public class ShadowTests {
    private final Intersectable sphere = new Sphere(60d, new Point(0, 0, -200))                                         //
            .setEmission(new Color(BLUE))                                                                                  //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
    private final Material trMaterial = new Material().setKd(0.5).setKs(0.5).setShininess(30);

    private Scene scene = new Scene.SceneBuilder("Test scene").build();
    private Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))   //
            .setVPSize(200, 200).setVPDistance(1000)                                                                       //
            .setRayTracer(new RayTracerBasic(scene));

    /**
     * Helper function for the tests in this module
     */
    void sphereTriangleHelper(String pictName, Triangle triangle, Point spotLocation) {
        scene.geometries.add(sphere, triangle.setEmission(new Color(BLUE)).setMaterial(trMaterial));
        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), spotLocation, new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));
        camera.setImageWriter(new ImageWriter(pictName, 400, 400)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere and triangle with point light and shade
     */
    @Test
    public void sphereTriangleInitial() {
        sphereTriangleHelper("shadowSphereTriangleInitial", //
                new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                new Point(-100, -100, 200));
    }

    /**
     * Sphere-Triangle shading - move triangle up-right
     */
    @Test
    public void sphereTriangleMove1() {
        sphereTriangleHelper("shadowSphereTriangleMove1", //
                new Triangle(new Point(-18, -49, 0), new Point(-49, -18, 0), new Point(-47, -47, -4)), //
                new Point(-100, -100, 200));
    }

    /**
     * Sphere-Triangle shading - move triangle upper-righter
     */
    @Test
    public void sphereTriangleMove2() {
        sphereTriangleHelper("shadowSphereTriangleMove2", //
                new Triangle(new Point(-33, -60, 0), new Point(-60, -33, 0), new Point(-57, -57, -4)), //
                new Point(-100, -100, 200));
    }

    /**
     * Sphere-Triangle shading - move spot closer
     */
    @Test
    public void sphereTriangleSpot1() {
        sphereTriangleHelper("shadowSphereTriangleSpot1", //
                new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                new Point(-90, -90, 130));
    }

    /**
     * Sphere-Triangle shading - move spot even more close
     */
    @Test
    public void sphereTriangleSpot2() {
        sphereTriangleHelper("shadowSphereTriangleSpot2", //
                new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                new Point(-77, -77, 70));
    }

    /**
     * Produce a picture of two triangles lighted by a spotlight with a Sphere
     * producing a shading
     */
    @Test
    public void trianglesSphere() {
        scene = new Scene.SceneBuilder("Test scene").setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();

        camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))   //
                .setVPSize(200, 200).setVPDistance(1000)                                                                       //
                .setRayTracer(new RayTracerBasic(scene));

        scene.geometries.add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                new Sphere(30d, new Point(0, 0, -11)) //
                        .setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );
        scene.lights.add( //
                new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
                        .setKl(4E-4).setKq(2E-5));

        camera.setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void f() {
        List<Intersectable.GeoPoint> inte = new Scene.SceneBuilder("egdf").setGeometries(new Geometries(List.of(new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), new Sphere(60d, new Point(0, 0, -200))))).build().geometries.findGeoIntersections(new Ray(new Point(-100, -100, 200), new Vector(1, 1, -3)));
        Intersectable.GeoPoint inter = new Ray(new Point(-100, -100, 200), new Vector(1, 1, -3)).findClosestGeoPoint(inte);
        System.out.println(new Point(0, 0, -36.96).distance(new Point(-14, -14, -36.96)));
        System.out.println(inter);

    }

}
