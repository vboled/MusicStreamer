import {Divider, List, Space} from "antd";
import {Link} from "react-router-dom";
import getCover from "./getCover";

function ArtistList(artists) {

    return (<div>
        <Divider orientation="left">Artists:</Divider>
        <List
            size="large"
            bordered
            rowKey
            dataSource={artists}
            renderItem={(item, index) =>
                <List.Item>
                    <Space size={100}>
                        <Link to={`/artist/${item.id}`}>
                            {getCover(item.uuid, 200, "artistDefault.png")}
                        </Link>
                        <Link to={`/artist/${item.id}`}>
                            <h1>{item.name}</h1>
                        </Link>
                    </Space>
                </List.Item>}
        /></div>)
}

export default ArtistList