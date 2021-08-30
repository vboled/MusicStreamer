import {Image} from "antd";
import {useEffect, useMemo, useState} from "react";

function GetImage(props) {

    const getName = () => {
        let name = props.uuid
        if (name === undefined)
            name = props.defaultName
        return window.location.origin + '/img/' + name
    }

    return <Image
        width={props.w}
        preview={false}
        src={getName()}
    />
}

export default GetImage