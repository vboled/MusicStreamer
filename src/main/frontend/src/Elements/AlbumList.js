import {Divider, List, Space} from "antd";
import {Link} from "react-router-dom";
import getCover from "./getCover";

function AlbumList(albums) {

    return (<div>
        <Divider orientation="left">Albums:</Divider>
        <List
            size="large"
            bordered
            rowKey
            dataSource={albums}
            renderItem={(item, index) =>
                <List.Item>
                    <Space size={100}>
                        <Link to={`/album/${item.id}`}>
                            {getCover(item.uuid, 200, 'playlistDefault.png')}
                        </Link>
                        <Link to={`/album/${item.id}`}>
                            <h1>{item.name}</h1>
                        </Link>
                    </Space>
                </List.Item>}
        /></div>)
}

export default AlbumList