package com.evon.jsontools;

import org.junit.Assert;
import org.junit.Test;

public class MapJsonTest {
    MapJson mapJson = new MapJson();

    @Test
    public void testSubtract() {
        Assert.assertEquals("{}", mapJson.subtract(null, null));
        Assert.assertEquals("{}", mapJson.subtract("{}", null));
        Assert.assertEquals("{}", mapJson.subtract(null, "{\"name\":\"evon\"}"));
        Assert.assertEquals("{}", mapJson.subtract("{}", "{\"name\":\"evon\"}"));
        Assert.assertEquals("{\"name\":\"evon\"}", mapJson.subtract("{\"name\":\"evon\"}", null));
        Assert.assertEquals("{}", mapJson.subtract("{\"name\":\"evon\"}", "{\"name\":\"evon\"}"));
        Assert.assertEquals("{\"name\":\"evon\"}", mapJson.subtract("{\"name\":\"evon\"}", "{\"phone\":\"13265479809\"}"));
        Assert.assertEquals("{}", mapJson.subtract("{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}",
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}"));
        Assert.assertEquals("{\"user\":{\"phone\":\"13265479809\"}}", mapJson.subtract(
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}",
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479810\"}}"));
        Assert.assertEquals("{\"user\":{\"phones\":[\"13265479810\"]}}", mapJson.subtract(
                "{\"user\":{\"name\":\"evon\",\"phones\":[\"13265479809\",\"13265479810\"]}}",
                "{\"user\":{\"name\":\"evon\",\"phones\":[\"13265479809\"]}}"));
        Assert.assertEquals("{}", mapJson.subtract("[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}]",
                "[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}]"));
        Assert.assertEquals("[{\"user\":{\"name\":\"john\",\"phone\":\"13265479810\"}}]",
                mapJson.subtract("[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}},{\"user\":{\"name\":\"john\",\"phone\":\"13265479810\"}}]",
                "[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}]"));
        Assert.assertEquals("{}", mapJson.subtract(
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}",
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479810\"}}", "user.phone"));
        Assert.assertEquals("{}", mapJson.subtract(
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}",
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479810\"}}", "phone"));

        Assert.assertEquals("[{\"user\":{\"name\":\"jhon\"}}]",
                mapJson.subtract("[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}},{\"user\":{\"name\":\"jhon\",\"phone\":\"13265479810\"}}]",
                        "[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}]","user.phone"));

        Assert.assertEquals("{\"total\":2,\"users\":[{\"user\":{\"name\":\"jhon\"}}]}",
                mapJson.subtract("{\"total\":2,\"users\":[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}},{\"user\":{\"name\":\"jhon\",\"phone\":\"13265479810\"}}]}",
                        "{\"total\":1,\"users\":[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}]}","user.phone"));

        Assert.assertEquals("{}",
                mapJson.subtract("{\"total\":1,\"users\":[{\"name\":\"evon\",\"phone\":\"13265479810\"}]}",
                        "{\"total\":1,\"users\":[{\"name\":\"evon\",\"phone\":\"13265479809\"}]}","users.phone"));
    }

    @Test
    public void testAddNotCover() {
        Assert.assertEquals("{}", mapJson.addNotCover(null, null));
        Assert.assertEquals("{}", mapJson.addNotCover("{}", null));
        Assert.assertEquals("{\"name\":\"evon\"}", mapJson.addNotCover(null, "{\"name\":\"evon\"}"));
        Assert.assertEquals("{\"name\":\"evon\"}", mapJson.addNotCover("{}", "{\"name\":\"evon\"}"));
        Assert.assertEquals("{\"name\":\"evon\"}", mapJson.addNotCover("{\"name\":\"evon\"}", null));
        Assert.assertEquals("{\"name\":\"evon\"}", mapJson.addNotCover("{\"name\":\"evon\"}", "{\"name\":\"evon\"}"));
        Assert.assertEquals("{\"name\":\"evon\",\"phone\":\"13265479809\"}", mapJson.addNotCover("{\"name\":\"evon\"}",
                "{\"phone\":\"13265479809\"}"));
        Assert.assertEquals("{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}", mapJson.addNotCover(
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}",
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}"));
        Assert.assertEquals("{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}", mapJson.addNotCover(
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}",
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479810\"}}"));
        Assert.assertEquals("{\"user\":{\"name\":\"evon\",\"phones\":[\"13265479809\",\"13265479810\"]}}", mapJson.addNotCover(
                "{\"user\":{\"name\":\"evon\",\"phones\":[\"13265479809\",\"13265479810\"]}}",
                "{\"user\":{\"name\":\"evon\",\"phones\":[\"13265479810\"]}}"));
        Assert.assertEquals("[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}},{\"user\":{\"name\":\"john\",\"phone\":\"13265479810\"}}]",
                mapJson.addNotCover("[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}},{\"user\":{\"name\":\"john\",\"phone\":\"13265479810\"}}]",
                "[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479810\"}}]"));
    }

    @Test
    public void testAddAndCover() {
        Assert.assertEquals("{}", mapJson.addAndCover(null, null));
        Assert.assertEquals("{}", mapJson.addAndCover("{}", null));
        Assert.assertEquals("{\"name\":\"evon\"}", mapJson.addAndCover(null, "{\"name\":\"evon\"}"));
        Assert.assertEquals("{\"name\":\"evon\"}", mapJson.addAndCover("{}", "{\"name\":\"evon\"}"));
        Assert.assertEquals("{\"name\":\"evon\"}", mapJson.addAndCover("{\"name\":\"evon\"}", null));
        Assert.assertEquals("{\"name\":\"evon\"}", mapJson.addAndCover("{\"name\":\"evon\"}", "{\"name\":\"evon\"}"));
        Assert.assertEquals("{\"name\":\"evon\",\"phone\":\"13265479809\"}", mapJson.addAndCover("{\"name\":\"evon\"}",
                "{\"phone\":\"13265479809\"}"));
        Assert.assertEquals("{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}", mapJson.addAndCover(
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}",
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}"));
        Assert.assertEquals("{\"user\":{\"name\":\"evon\",\"phone\":\"13265479810\"}}", mapJson.addAndCover(
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}",
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479810\"}}"));
        Assert.assertEquals("{\"user\":{\"name\":\"evon\",\"phones\":[\"13265479810\",\"13265479810\"]}}", mapJson.addAndCover(
                "{\"user\":{\"name\":\"evon\",\"phones\":[\"13265479809\",\"13265479810\"]}}",
                "{\"user\":{\"name\":\"evon\",\"phones\":[\"13265479810\"]}}"));
        Assert.assertEquals("[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479810\"}},{\"user\":{\"name\":\"john\",\"phone\":\"13265479810\"}}]",
                mapJson.addAndCover("[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}},{\"user\":{\"name\":\"john\",\"phone\":\"13265479810\"}}]",
                        "[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479810\"}}]"));
    }

    @Test
    public void testCompare() {
        Assert.assertEquals("{}", mapJson.compare(null, null));
        Assert.assertEquals("{}", mapJson.compare("{}", null));
        Assert.assertEquals("{}", mapJson.compare(null, "{\"name\":\"evon\"}"));
        Assert.assertEquals("{}", mapJson.compare("{}", "{\"name\":\"evon\"}"));
        Assert.assertEquals("{\"name\":false}", mapJson.compare("{\"name\":\"evon\"}", null));
        Assert.assertEquals("{\"name\":true}", mapJson.compare("{\"name\":\"evon\"}", "{\"name\":\"evon\"}"));
        Assert.assertEquals("{\"name\":false}", mapJson.compare("{\"name\":\"evon\"}",
                "{\"phone\":\"13265479809\"}"));
        Assert.assertEquals("{\"user\":{\"name\":true,\"phone\":true}}", mapJson.compare(
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}",
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}"));
        Assert.assertEquals("{\"user\":{\"name\":true,\"phone\":false}}", mapJson.compare(
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}",
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479810\"}}"));
        Assert.assertEquals("{\"user\":{\"name\":true,\"phones\":[true,false]}}", mapJson.compare(
                "{\"user\":{\"name\":\"evon\",\"phones\":[\"13265479809\",\"13265479810\"]}}",
                "{\"user\":{\"name\":\"evon\",\"phones\":[\"13265479809\"]}}"));
        Assert.assertEquals("[{\"user\":{\"name\":true,\"phone\":false}},{\"user\":{\"name\":false,\"phone\":false}}]",
                mapJson.compare("[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}},{\"user\":{\"name\":\"john\",\"phone\":\"13265479810\"}}]",
                        "[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479810\"}}]"));

        Assert.assertEquals("[{\"user\":{\"name\":true,\"phone\":true}},{\"user\":{\"name\":false,\"phone\":true}}]",
                mapJson.compare("[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}},{\"user\":{\"name\":\"jhon\",\"phone\":\"13265479810\"}}]",
                        "[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}]","user.phone"));

        Assert.assertEquals("{\"total\":false,\"users\":[{\"user\":{\"name\":true,\"phone\":true}},{\"user\":{\"name\":false,\"phone\":true}}]}",
                mapJson.compare("{\"total\":2,\"users\":[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}},{\"user\":{\"name\":\"jhon\",\"phone\":\"13265479810\"}}]}",
                        "{\"total\":1,\"users\":[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}]}","user.phone"));

        Assert.assertEquals("{\"total\":true,\"users\":[{\"name\":true,\"phone\":true}]}",
                mapJson.compare("{\"total\":1,\"users\":[{\"name\":\"evon\",\"phone\":\"13265479810\"}]}",
                        "{\"total\":1,\"users\":[{\"name\":\"evon\",\"phone\":\"13265479809\"}]}","users.phone"));
    }

    @Test
    public void testCompareReturnFalse() {
        Assert.assertEquals("{}", mapJson.compareReturnFalse(null, null));
        Assert.assertEquals("{}", mapJson.compareReturnFalse("{}", null));
        Assert.assertEquals("{}", mapJson.compareReturnFalse(null, "{\"name\":\"evon\"}"));
        Assert.assertEquals("{}", mapJson.compareReturnFalse("{}", "{\"name\":\"evon\"}"));
        Assert.assertEquals("{\"name\":false}", mapJson.compareReturnFalse("{\"name\":\"evon\"}", null));
        Assert.assertEquals("{}", mapJson.compareReturnFalse("{\"name\":\"evon\"}", "{\"name\":\"evon\"}"));
        Assert.assertEquals("{\"name\":false}", mapJson.compareReturnFalse("{\"name\":\"evon\"}",
                "{\"phone\":\"13265479809\"}"));
        Assert.assertEquals("{}", mapJson.compareReturnFalse(
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}",
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}"));
        Assert.assertEquals("{\"user\":{\"phone\":false}}", mapJson.compareReturnFalse(
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}",
                "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479810\"}}"));
        Assert.assertEquals("{\"user\":{\"phones\":[false]}}", mapJson.compareReturnFalse(
                "{\"user\":{\"name\":\"evon\",\"phones\":[\"13265479809\",\"13265479810\"]}}",
                "{\"user\":{\"name\":\"evon\",\"phones\":[\"13265479809\"]}}"));
        Assert.assertEquals("{}", mapJson.subtract(
                "{\"homePageV6\":{\"valueAddServiceEntity\":[{\"a1\":false},{\"a1\":false}],\"courierServiceBG\":{\"a2\":false},\"consumerServiceEntity\":[{\"a3\":false,}],\"consumerServiceBG\":{\"a4\":false},\"banner\":{\"a5\":false},\"valueAddServiceBG\":{\"a6\":false},\"fashion\":{\"a7\":false}},\"homePageV5\":{\"banner\":{\"a8\":false},\"fashion\":{\"a9\":false}}}",
                mapJson.compareReturnFalse(
                "{\"homePageV6\":{\"valueAddServiceEntity\":[{\"a1\":6},{\"a1\":6}],\"courierServiceBG\":{\"a2\":6},\"consumerServiceEntity\":[{\"a3\":\"storage_save\",}],\"consumerServiceBG\":{\"a4\":6},\"banner\":{\"a5\":6},\"valueAddServiceBG\":{\"a6\":6},\"fashion\":{\"a7\":6}},\"homePageV5\":{\"banner\":{\"a8\":6},\"fashion\":{\"a9\":6}}}",
                "{\"homePageV6\":{\"valueAddServiceEntity\":[{\"a1\":5}],\"courierServiceBG\":{\"a2\":5},\"consumerServiceEntity\":[{\"a3\":\"storage_save2\",},{\"a3\":\"storage_save11\",}],\"consumerServiceBG\":{\"a4\":5},\"banner\":{\"a5\":5},\"valueAddServiceBG\":{\"a6\":5},\"fashion\":{\"a7\":5}}}")));
        Assert.assertEquals("{}", mapJson.subtract(
                "{\"homePageV6\":{\"valueAddServiceEntity\":[{\"a1\":false}],\"banner\":{\"a5\":false},\"valueAddServiceBG\":{\"a6\":false},\"fashion\":{\"a7\":false}},\"homePageV5\":{\"banner\":{\"a8\":false},\"fashion\":{\"a9\":false}}}",
                mapJson.compareReturnFalse(
                        "{\"homePageV6\":{\"valueAddServiceEntity\":[{\"a1\":6},{\"a1\":6}],\"courierServiceBG\":{\"a2\":6},\"consumerServiceEntity\":[{\"a3\":\"storage_save\",}],\"consumerServiceBG\":{\"a4\":6},\"banner\":{\"a5\":6},\"valueAddServiceBG\":{\"a6\":6},\"fashion\":{\"a7\":6}},\"homePageV5\":{\"banner\":{\"a8\":6},\"fashion\":{\"a9\":6}}}",
                        "{\"homePageV6\":{\"valueAddServiceEntity\":[{\"a1\":6}],\"courierServiceBG\":{\"a2\":6},\"consumerServiceEntity\":[{\"a3\":\"storage_save\",},{\"a3\":\"storage_save11\",}],\"consumerServiceBG\":{\"a4\":6},\"banner\":{\"a5\":5},\"valueAddServiceBG\":{\"a6\":5},\"fashion\":{\"a7\":5}}}")));
        Assert.assertEquals("{}", mapJson.subtract(
                "{\"homePageV6\":{\"valueAddServiceEntity\":[{\"a1\":false}],\"valueAddServiceBG\":{\"a6\":false}}}",
                mapJson.compareReturnFalse(
                        "{\"homePageV6\":{\"valueAddServiceEntity\":[{\"a1\":6},{\"a1\":6}],\"courierServiceBG\":{\"a2\":6},\"consumerServiceEntity\":[{\"a3\":\"storage_save\",}],\"consumerServiceBG\":{\"a4\":6},\"banner\":{\"a5\":6},\"valueAddServiceBG\":{\"a6\":6},\"fashion\":{\"a7\":6}},\"homePageV5\":{\"banner\":{\"a8\":6},\"fashion\":{\"a9\":6}}}",
                        "{\"homePageV6\":{\"valueAddServiceEntity\":[{\"a1\":6}],\"courierServiceBG\":{\"a2\":6},\"consumerServiceEntity\":[{\"a3\":\"storage_save\",},{\"a3\":\"storage_save11\",}],\"consumerServiceBG\":{\"a4\":6},\"banner\":{\"a5\":5},\"valueAddServiceBG\":{\"a6\":5},\"fashion\":{\"a7\":5}}}",
                        new String[]{"banner","fashion"})));
        Assert.assertEquals("{}", mapJson.subtract(
                "{}",
                mapJson.compareReturnFalse(
                        "{\"homePageV6\":{\"valueAddServiceEntity\":[{\"a1\":6},{\"a1\":6}],\"courierServiceBG\":{\"a2\":6},\"consumerServiceEntity\":[{\"a3\":\"storage_save\",}],\"consumerServiceBG\":{\"a4\":6},\"banner\":{\"a5\":6},\"valueAddServiceBG\":{\"a6\":6},\"fashion\":{\"a7\":6}},\"homePageV5\":{\"banner\":{\"a8\":6},\"fashion\":{\"a9\":6}}}",
                        "{\"homePageV6\":{\"valueAddServiceEntity\":[{\"a1\":6}],\"courierServiceBG\":{\"a2\":6},\"consumerServiceEntity\":[{\"a3\":\"storage_save\",},{\"a3\":\"storage_save11\",}],\"consumerServiceBG\":{\"a4\":6},\"banner\":{\"a5\":5},\"valueAddServiceBG\":{\"a6\":5},\"fashion\":{\"a7\":5}}}",
                        new String[]{"banner","fashion","valueAddServiceEntity","valueAddServiceBG"})));
        Assert.assertEquals("[{\"user\":{\"phone\":false}},{\"user\":{\"name\":false,\"phone\":false}}]",
                mapJson.compareReturnFalse("[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}},{\"user\":{\"name\":\"john\",\"phone\":\"13265479810\"}}]",
                        "[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479810\"}}]"));

        Assert.assertEquals("[{\"user\":{\"name\":false}}]",
                mapJson.compareReturnFalse("[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}},{\"user\":{\"name\":\"jhon\",\"phone\":\"13265479810\"}}]",
                        "[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}]","user.phone"));

        Assert.assertEquals("{\"total\":false,\"users\":[{\"user\":{\"name\":false}}]}",
                mapJson.compareReturnFalse("{\"total\":2,\"users\":[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}},{\"user\":{\"name\":\"jhon\",\"phone\":\"13265479810\"}}]}",
                        "{\"total\":1,\"users\":[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}]}","user.phone"));

        Assert.assertEquals("{}",
                mapJson.compareReturnFalse("{\"total\":1,\"users\":[{\"name\":\"evon\",\"phone\":\"13265479810\"}]}",
                        "{\"total\":1,\"users\":[{\"name\":\"evon\",\"phone\":\"13265479809\"}]}","users.phone"));
    }

    @Test
    public void testGetValue() {
        MapJson mapJson = new MapJson("{\"user\":{\"name\":\"evon\",\"phones\":[\"13265479809\",\"13265479810\"]},\"role\":[{\"type\":{\"name\":\"student\",\"code\":\"s\"}}],\"id\":16}");
        Assert.assertEquals("evon", mapJson.getValue("user.name"));
        Assert.assertEquals("13265479809", mapJson.getValue("user.phones[0]"));
        Assert.assertEquals("13265479810", mapJson.getValue("user.phones[1]"));
        Assert.assertEquals(16, mapJson.getValue("id"));
        Assert.assertEquals("student", mapJson.getValue("role[0].type.name"));
        Assert.assertEquals("s", mapJson.getValue("role[0].type.code"));
    }

    @Test
    public void testGetValue4List() {
        MapJson mapJson = new MapJson("[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}},{\"user\":{\"name\":\"john\",\"phone\":\"13265479810\"}}]");
        Assert.assertEquals("evon", mapJson.getValue("root[0].user.name"));
        Assert.assertEquals("13265479809", mapJson.getValue("root[0].user.phone"));
        Assert.assertEquals("john", mapJson.getValue("root[1].user.name"));
        Assert.assertEquals("13265479810", mapJson.getValue("root[1].user.phone"));
    }

    @Test
    public void testCompareAndReturnValue() {
        Assert.assertEquals("{\"user\":{\"phone\":\"13265479809->13265479810\"}}", mapJson.compareReturnValue("{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}}", "{\"user\":{\"name\":\"evon\",\"phone\":\"13265479810\"}}"));
        Assert.assertEquals("[{\"user\":{\"phone\":\"13265479809->13265479810\"}},{\"user\":{\"name\":\"john-> \",\"phone\":\"13265479810-> \"}}]",
                mapJson.compareReturnValue("[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479809\"}},{\"user\":{\"name\":\"john\",\"phone\":\"13265479810\"}}]",
                        "[{\"user\":{\"name\":\"evon\",\"phone\":\"13265479810\"}}]"));
    }
}
