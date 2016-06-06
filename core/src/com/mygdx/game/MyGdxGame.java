package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationListener;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.UBJsonReader;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    private Model model;
    private Model man_model;
    private ModelInstance modelInstance;
    
    private AssetManager assetManager;
    private G3dModelLoader g3dbModelLoader;
    private G3dModelLoader g3djModelLoader;
    private ObjLoader objLoader;
    
    private ModelBatch modelBatch;
    private PerspectiveCamera cam;
    private CameraInputController camController;
    public ModelInstance space;
    public ModelInstance man;
    
    public Environment environment;
    AnimationController animationcontroller;
    private Vector3 dims;
	@Override
	public void create () {
		
        objLoader = new ObjLoader();
        g3dbModelLoader = new G3dModelLoader(new UBJsonReader());
        g3djModelLoader = new G3dModelLoader(new JsonReader());
        
		batch = new SpriteBatch();
		modelBatch = new ModelBatch();
		
		img = new Texture("badlogic.jpg");
        String absolutePath = "C:/Users/nanjusoil/Desktop/test2/core/assets/Street environment_V01.g3db";
        String manPath = "C:/Users/nanjusoil/Desktop/test2/core/assets/Maskboy.g3db";
        String spherePath = "C:/Users/nanjusoil/Desktop/test2/core/assets/spacesphere.obj";

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0, 0, 0);
        cam.near = 10f;
        cam.far = 300f;
        cam.update();
        
        camController = new CameraInputController(null);
        camController.translateUnits = 100f;
        Gdx.input.setInputProcessor(camController);
        camController.camera = cam;
        if (absolutePath.toLowerCase().endsWith("obj")) {
                model = objLoader.loadModel(Gdx.files.absolute(absolutePath));
        } else if (absolutePath.toLowerCase().endsWith("g3dj")) {
                model = g3djModelLoader.loadModel(Gdx.files.absolute(absolutePath));
        } else {
                model = g3dbModelLoader.loadModel(Gdx.files.absolute(absolutePath));
        }
        
        space = new ModelInstance(objLoader.loadModel(Gdx.files.absolute(spherePath)));
        man_model = g3dbModelLoader.loadModel(Gdx.files.absolute(manPath));
        man = new ModelInstance(man_model);
        man.transform.scale((float)0.1, (float)0.1, (float)0.1);
        modelInstance = new ModelInstance(model);
        System.out.println(man.nodes.get(1).id);
        animationcontroller = new AnimationController(man);
        
        dims= new Vector3();
        dims.x = 0;
        dims.y = 0;
        dims.z = 0;

	}

	@Override
	public void render () {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(1, 1, 1, 1);
        //Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		//Gdx.gl.glClearColor(1, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//batch.begin();
		//batch.draw(img, 0, 0);
		//batch.end();
        
        animationcontroller.update(Gdx.graphics.getDeltaTime());
		   if(Gdx.input.isKeyPressed(Input.Keys.Z)) {
			AnimationListener listener = null;
			animationcontroller.setAnimation("Take 001", (float) 0.3, (float)0.7, 1, 1, listener);		        
			   }
		   if(Gdx.input.isKeyPressed(Input.Keys.Y)) {
			   animationcontroller.setAnimation(null);
			   }


		camController.update();
		   if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
		        dims.x++;
			   	man.transform.setTranslation(dims);
			   }
		   if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
		        dims.x--;
			   	man.transform.setTranslation(dims);
			   }
		   if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
		        dims.z++;
			   	man.transform.setTranslation(dims);
			   }
		   if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
		        dims.z--;
			   	man.transform.setTranslation(dims);
			   }
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        if (modelInstance != null) {
        	
            modelBatch.begin(cam);
            modelBatch.render(modelInstance, environment);
            modelBatch.render(space, environment);
            modelBatch.render(man, environment);
            //man.tran
            modelBatch.end();
        }

	}
}
