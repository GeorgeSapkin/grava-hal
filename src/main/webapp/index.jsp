<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Grava Hal</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">

    <link href="css/grava.css" rel="stylesheet" type="text/css">

    <script src="js/jquery-2.1.4.min.js"></script>
    <script src="js/grava.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            connect();
        });
    </script>
</head>
<body>

<div>
    <div>
        <label id="info" />
    </div>
    <div>
        <table>
            <tr>
                <td class="pit opponnetsPit" rowspan="2"><label id="p13">0</label></td>
                <td class="pit opponnetsPit"><label id="p12">0</label></td>
                <td class="pit opponnetsPit"><label id="p11">0</label></td>
                <td class="pit opponnetsPit"><label id="p10">0</label></td>
                <td class="pit opponnetsPit"><label id="p9">0</label></td>
                <td class="pit opponnetsPit"><label id="p8">0</label></td>
                <td class="pit opponnetsPit"><label id="p7">0</label></td>
                <td class="pit myPit" rowspan="2"><label id="p6">0</label></td>
            </tr>
            <tr>
                <td class="pit myPit"><label id="p0">0</label></td>
                <td class="pit myPit"><label id="p1">0</label></td>
                <td class="pit myPit"><label id="p2">0</label></td>
                <td class="pit myPit"><label id="p3">0</label></td>
                <td class="pit myPit"><label id="p4">0</label></td>
                <td class="pit myPit"><label id="p5">0</label></td>
            </tr>
            <tr>
                <td></td>
                <td class="pit"><button id="sowButton0" onclick="sow(0);" disabled>&gt;</button></td>
                <td class="pit"><button id="sowButton1" onclick="sow(1);" disabled>&gt;</button></td>
                <td class="pit"><button id="sowButton2" onclick="sow(2);" disabled>&gt;</button></td>
                <td class="pit"><button id="sowButton3" onclick="sow(3);" disabled>&gt;</button></td>
                <td class="pit"><button id="sowButton4" onclick="sow(4);" disabled>&gt;</button></td>
                <td class="pit"><button id="sowButton5" onclick="sow(5);" disabled>&gt;</button></td>
                <td></td>
            </tr>
        </table>
    </div>
</div>

</body>
</html>