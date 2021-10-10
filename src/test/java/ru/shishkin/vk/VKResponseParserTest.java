package ru.shishkin.vk;

import org.junit.Assert;
import org.junit.Test;
import ru.shishkin.domain.Post;
import ru.shishkin.domain.PostsHolder;

import java.util.List;

/**
 * @author akirakozov
 */
public class VKResponseParserTest {
    private final static String testResponse =
            "{\n" +
                    "   \"response\":{\n" +
                    "      \"items\":[\n" +
                    "         {\n" +
                    "            \"id\":1367,\n" +
                    "            \"date\":1633196700,\n" +
                    "            \"owner_id\":-77617250,\n" +
                    "            \"from_id\":-77617250,\n" +
                    "            \"post_type\":\"post\",\n" +
                    "            \"text\":\"Уже завтра Московский Парк Победы примет старт нашего Клубного марафона 2021\uD83D\uDE31\uD83D\uDD25\\n\\nИнформация по программе:\\n✅Регистрация, получение номеров с 9.00 до 9.50\\n\uD83D\uDC6F\u200D♂️С 9.30 до 9.50 разминка с [club58562062|самыми прекрасными и спортивными]\\n\uD83C\uDFC3\u200D♂️Старт на 10 км - 10.00\\n\uD83C\uDFC3\u200D♂️Старт на 5 км - 10.15\\n\\n❓Где: Московский Парк Победы, метро Парк Победы\\n❗Когда: завтра в 9.00\\n#kronbars#athletics#itmo#итмо \\n#ITMORUN2021\",\n" +
                    "            \"marked_as_ads\":0,\n" +
                    "            \"attachments\":[\n" +
                    "               {\n" +
                    "                  \"type\":\"photo\",\n" +
                    "                  \"photo\":{\n" +
                    "                     \"album_id\":-7,\n" +
                    "                     \"date\":1633195633,\n" +
                    "                     \"id\":457239851,\n" +
                    "                     \"owner_id\":-77617250,\n" +
                    "                     \"has_tags\":false,\n" +
                    "                     \"access_key\":\"783deded4850e4fce8\",\n" +
                    "                     \"post_id\":1367,\n" +
                    "                     \"sizes\":[\n" +
                    "                        {\n" +
                    "                           \"height\":130,\n" +
                    "                           \"url\":\"https:\\/\\/sun9-18.userapi.com\\/impg\\/PiZWfVW1O90lEG8DdC0W5aZ6aD1NWGAjpr4Sgw\\/-6-hG7BF4lk.jpg?size=130x130&quality=96&sign=eb1798de5ba9eb28f931d6064daadfb7&c_uniq_tag=VyJ9e1XY6_7n31xw_ObvtOkkGbPSrbF-oxZtzj2VNHw&type=album\",\n" +
                    "                           \"type\":\"m\",\n" +
                    "                           \"width\":130\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                           \"height\":130,\n" +
                    "                           \"url\":\"https:\\/\\/sun9-18.userapi.com\\/impg\\/PiZWfVW1O90lEG8DdC0W5aZ6aD1NWGAjpr4Sgw\\/-6-hG7BF4lk.jpg?size=130x130&quality=96&sign=eb1798de5ba9eb28f931d6064daadfb7&c_uniq_tag=VyJ9e1XY6_7n31xw_ObvtOkkGbPSrbF-oxZtzj2VNHw&type=album\",\n" +
                    "                           \"type\":\"o\",\n" +
                    "                           \"width\":130\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                           \"height\":200,\n" +
                    "                           \"url\":\"https:\\/\\/sun9-18.userapi.com\\/impg\\/PiZWfVW1O90lEG8DdC0W5aZ6aD1NWGAjpr4Sgw\\/-6-hG7BF4lk.jpg?size=200x200&quality=96&sign=f6d1704dc7c104daa2eae0adf9df2471&c_uniq_tag=xZVrFu2fVjoUjASbxfycEhW5YIYrEAYE3dCgsA8F-lY&type=album\",\n" +
                    "                           \"type\":\"p\",\n" +
                    "                           \"width\":200\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                           \"height\":320,\n" +
                    "                           \"url\":\"https:\\/\\/sun9-18.userapi.com\\/impg\\/PiZWfVW1O90lEG8DdC0W5aZ6aD1NWGAjpr4Sgw\\/-6-hG7BF4lk.jpg?size=320x320&quality=96&sign=9de01e6b3d0bfb5a5db3c7d63b5a077c&c_uniq_tag=t7Td7GR1yzyRkV4FLjRVp4Ut6aVxS23VNa10B0GHL4k&type=album\",\n" +
                    "                           \"type\":\"q\",\n" +
                    "                           \"width\":320\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                           \"height\":510,\n" +
                    "                           \"url\":\"https:\\/\\/sun9-18.userapi.com\\/impg\\/PiZWfVW1O90lEG8DdC0W5aZ6aD1NWGAjpr4Sgw\\/-6-hG7BF4lk.jpg?size=510x510&quality=96&sign=6847a94c6aca30e40c9422eaf521fbd5&c_uniq_tag=M-O569H52kD7qsnOAteocOcC4rV51rmEkpMW_zN9rx4&type=album\",\n" +
                    "                           \"type\":\"r\",\n" +
                    "                           \"width\":510\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                           \"height\":75,\n" +
                    "                           \"url\":\"https:\\/\\/sun9-18.userapi.com\\/impg\\/PiZWfVW1O90lEG8DdC0W5aZ6aD1NWGAjpr4Sgw\\/-6-hG7BF4lk.jpg?size=75x75&quality=96&sign=a4394e1ed816b7d3175d23a9cfb4dc62&c_uniq_tag=zsmk3uh1QbI8WhGLw46xRYO0EEDMUxkPJECnPRRv2Uw&type=album\",\n" +
                    "                           \"type\":\"s\",\n" +
                    "                           \"width\":75\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                           \"height\":604,\n" +
                    "                           \"url\":\"https:\\/\\/sun9-18.userapi.com\\/impg\\/PiZWfVW1O90lEG8DdC0W5aZ6aD1NWGAjpr4Sgw\\/-6-hG7BF4lk.jpg?size=604x604&quality=96&sign=a5e62cf2c626bf60711bfdd2bb9f46b1&c_uniq_tag=Zo221AhvjQn2Rr92K3MTUyCwE-xd8Z0vSGbe0aNV5ac&type=album\",\n" +
                    "                           \"type\":\"x\",\n" +
                    "                           \"width\":604\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                           \"height\":807,\n" +
                    "                           \"url\":\"https:\\/\\/sun9-18.userapi.com\\/impg\\/PiZWfVW1O90lEG8DdC0W5aZ6aD1NWGAjpr4Sgw\\/-6-hG7BF4lk.jpg?size=807x807&quality=96&sign=b220f3fd9e6815e55ee8393ffa97ebcd&c_uniq_tag=64l6E40b54z3PcMsA2jan64Y2Ayk9pN1jLYx8trKIpU&type=album\",\n" +
                    "                           \"type\":\"y\",\n" +
                    "                           \"width\":807\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                           \"height\":1000,\n" +
                    "                           \"url\":\"https:\\/\\/sun9-18.userapi.com\\/impg\\/PiZWfVW1O90lEG8DdC0W5aZ6aD1NWGAjpr4Sgw\\/-6-hG7BF4lk.jpg?size=1000x1000&quality=96&sign=a82704334421eaf418a7acd838e9998e&c_uniq_tag=qNOCz82dNVrGOv5w7gR1pWeADfa-q8JKFXIQI8MP2xI&type=album\",\n" +
                    "                           \"type\":\"z\",\n" +
                    "                           \"width\":1000\n" +
                    "                        }\n" +
                    "                     ],\n" +
                    "                     \"text\":\"\",\n" +
                    "                     \"user_id\":100\n" +
                    "                  }\n" +
                    "               }\n" +
                    "            ],\n" +
                    "            \"post_source\":{\n" +
                    "               \"platform\":\"android\",\n" +
                    "               \"type\":\"api\"\n" +
                    "            },\n" +
                    "            \"comments\":{\n" +
                    "               \"can_post\":1,\n" +
                    "               \"count\":1,\n" +
                    "               \"groups_can_post\":true\n" +
                    "            },\n" +
                    "            \"likes\":{\n" +
                    "               \"can_like\":1,\n" +
                    "               \"count\":14,\n" +
                    "               \"user_likes\":0,\n" +
                    "               \"can_publish\":1\n" +
                    "            },\n" +
                    "            \"reposts\":{\n" +
                    "               \"count\":3,\n" +
                    "               \"user_reposted\":0\n" +
                    "            },\n" +
                    "            \"views\":{\n" +
                    "               \"count\":583\n" +
                    "            },\n" +
                    "            \"donut\":{\n" +
                    "               \"is_donut\":false\n" +
                    "            },\n" +
                    "            \"short_text_rate\":0.800000\n" +
                    "         }\n" +
                    "      ],\n" +
                    "      \"count\":1,\n" +
                    "      \"total_count\":1\n" +
                    "   }\n" +
                    "}";

    @Test
    public void parseResponse() throws Exception {
        VKResponseParser parser = new VKResponseParser();
        PostsHolder holder = parser.parseResponse(testResponse);

        Assert.assertEquals(1, holder.getCount());

        Assert.assertEquals(holder.getAvailablePosts().get(0),
                new Post(1367,
                        1633196700,
                        "Уже завтра Московский Парк Победы примет старт нашего Клубного марафона 2021\uD83D\uDE31\uD83D\uDD25\n\nИнформация по программе:\n✅Регистрация, получение номеров с 9.00 до 9.50\n\uD83D\uDC6F\u200D♂️С 9.30 до 9.50 разминка с [club58562062|самыми прекрасными и спортивными]\n\uD83C\uDFC3\u200D♂️Старт на 10 км - 10.00\n\uD83C\uDFC3\u200D♂️Старт на 5 км - 10.15\n\n❓Где: Московский Парк Победы, метро Парк Победы\n❗Когда: завтра в 9.00\n#kronbars#athletics#itmo#итмо \n#ITMORUN2021"));
    }
}
