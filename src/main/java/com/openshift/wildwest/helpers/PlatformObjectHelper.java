package com.openshift.wildwest.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.openshift.wildwest.models.PlatformObject;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.client.KubernetesClient;


public class PlatformObjectHelper {

    KubernetesClient client;

	public PlatformObjectHelper(KubernetesClient client) {
		this.client = client;
	}

	public List<PlatformObject> getPlatformObjects() {

		ArrayList<PlatformObject> platformObjects = new ArrayList<>();
		platformObjects.addAll(this.getPods());
		platformObjects.addAll(this.getPVs());
		platformObjects.addAll(this.getServices());
		platformObjects.addAll(this.getRoutes());

		return platformObjects;

	}

	public PlatformObject getRandomPlatformObject() {
		List<PlatformObject> theObjects = this.getPlatformObjects();
		

		return theObjects.get(new Random().nextInt(theObjects.size()));
	}

	private List<PlatformObject> getPods() {
		ArrayList<PlatformObject> thePods = new ArrayList<>();

		List<Pod> pods = client.pods().list().getItems();
		for (Pod currPod : pods) {
			thePods.add(new PlatformObject(currPod.getMetadata().getUid(), currPod.getMetadata().getName(), "POD"));
		}
		return thePods;
	}

	private List<PlatformObject> getBuilds() {
		ArrayList<PlatformObject> theBuilds = new ArrayList<>();

		//List<Build> builds = client.builds().list().getItems();
		//for (Build currBuild : builds) {
		//	theBuilds.add(
		//			new PlatformObject(currBuild.getMetadata().getUid(), currBuild.getMetadata().getName(), "BUILD"));
		//}

		return theBuilds;
	}

	private List<PlatformObject> getDeploymentConfigs() {
		ArrayList<PlatformObject> theDeployments = new ArrayList<>();
		//List<DeploymentConfig> deploymentConfigs = client.deploymentConfigs().list().getItems();
		//for (DeploymentConfig currConfig : deploymentConfigs) {
		//	theDeployments.add(new PlatformObject(currConfig.getMetadata().getUid(), currConfig.getMetadata().getName(), "DEPLOYMENT_CONFIG"));
		//}

		return theDeployments;
	}


	private List<PlatformObject> getBuildConfigs() {
		ArrayList<PlatformObject> theList = new ArrayList<>();
		//List<BuildConfig> theItems = client.buildConfigs().list().getItems();
		//for (BuildConfig currConfig : theItems) {
		//	theList.add(new PlatformObject(currConfig.getMetadata().getUid(), currConfig.getMetadata().getName(),"BUILD_CONFIG"));
		//}
		return theList;
	}

	private List<PlatformObject> getPVs() {
		ArrayList<PlatformObject> theList = new ArrayList<>();
		List<PersistentVolumeClaim> theItems = client.persistentVolumeClaims().list().getItems();
		for (PersistentVolumeClaim currConfig : theItems) {
			theList.add(
					new PlatformObject(currConfig.getMetadata().getUid(), currConfig.getMetadata().getName(), "PVC"));
		}
		return theList;
	}

	private List<PlatformObject> getServices() {
		ArrayList<PlatformObject> theList = new ArrayList<>();
		List<Service> theItems = client.services().list().getItems();
		for (Service currConfig : theItems) {
			theList.add(new PlatformObject(currConfig.getMetadata().getUid(), currConfig.getMetadata().getName(),
					"SERVICE"));
		}
		return theList;
	}

	private List<PlatformObject> getRoutes() {
		ArrayList<PlatformObject> theList = new ArrayList<>();
		//List<Route> theItems = client.routes().list().getItems();
		//for (Route currConfig : theItems) {
		//	theList.add(
		//			new PlatformObject(currConfig.getMetadata().getUid(), currConfig.getMetadata().getName(), "ROUTE"));
		//}
		return theList;
	}

	public void deletePlatformObject(String gameID, String objectID, String objectType, String objectName) {

		// right now only delete things we can recover from
		switch (objectType) {
			case "POD":
				client.pods().withName(objectName).delete();
				break;
			//case "BUILD":
				//client.builds().withName(objectName).delete();
				//break;
		}
		
	}

}
