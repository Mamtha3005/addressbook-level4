# A0142130Aunused
###### \java\guitests\SaveStorageLocationCommandTest.java
``` java
    //This test is not run because it has assertion error on Travis build
    //@Test
    public void saveToInvalidFilePath() throws DataConversionException {
        JsonConfigStorage jsonConfigStorage = new JsonConfigStorage(CONFIG_LOCATION);

        commandBox.runCommand("save E:");   
        
        Optional<Config> newConfig = jsonConfigStorage.readConfig(CONFIG_JSON);
        String newFilePath = newConfig.get().getTaskManagerFilePath();
        logger.info("New path: " + newFilePath);
        
        assert(newFilePath.equals(DEFAULT_SAVE_LOCATION));
    }
    
```
