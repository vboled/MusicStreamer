import {Button, Divider, List, Space, Tooltip} from "antd";
import {CaretRightOutlined} from "@ant-design/icons";
import {Link} from "react-router-dom";

function SongList(songs) {

    return (<div>
    <Divider orientation="left">Songs:</Divider>
        <List
            size="small"
            bordered
            rowKey
            dataSource={songs}
            renderItem={(item, index) =>
                <List.Item>
                    <Space>
                        {index + 1}
                        <Tooltip title="Play">
                            <Button type="primary" shape="circle" icon={<CaretRightOutlined />} />
                        </Tooltip>
                        {item.title}
                        <Link to={`/artist/${item.artist.id}`}>
                            {item.artist.name}
                        </Link>
                        <Link to={`/album/${item.album.id}`}>
                            {item.album.name}
                        </Link>
                    </Space>
                </List.Item>}
        /></div>)
}

export default SongList