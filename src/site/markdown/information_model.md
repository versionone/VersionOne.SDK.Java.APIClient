## The VersionOne Information Model

Practically all data in VersionOne is stored in the form of assets, which have attributes. Each asset is classified by an asset type, which describes a number of attribute definitions, operations, rules, and possibly an inheritance from another asset type. A list of all the types within VersionOne can be obtained by accessing the meta data url of your VersionOne instance. Additionally, VersionOne comes with an xsl stylesheet, which can be referenced as a parameter to the meta data url and makes it easier to read the response:
```
http://localhost/VersionOne/meta.v1/?xsl=api.xsl
```
Individual types can also be viewed through the meta url:
```
http://localhost/VersionOne/meta.v1/Story?xsl=api.xsl
```
You must use the system name for the type you would like to retrieve. This is true whether using the API directly or the APIClient. For instance, in the example above the system name is "Story", which certain methodology templates display as "Backlog Item" or "Requirement". Here is a list of some of the most important system names and their corresponding default display names in the available methodology templates:

    <table cellspacing="0" cellpadding="0" border="1" width="100%">
        <thead>
            <tr>
                <th>System Name</th>
                <th>XP Display Name</th>
                <th>Scrum Display Name</th>
                <th>AgileUP Display Name</th>
                <th>DSDM Display Name</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Scope</td>
                <td>Project</td>
                <td>Project</td>
                <td>Project</td>
                <td>Project</td>
            </tr>
            <tr>
                <td>Timebox</td>
                <td>Iteration</td>
                <td>Sprint</td>
                <td>Iteration</td>
                <td>Iteration</td>
            </tr>
            <tr>
                <td>Theme</td>
                <td>Theme</td>
                <td>Feature Group</td>
                <td>Use Case</td>
                <td>Feature Group</td>
            </tr>
            <tr>
                <td>Story</td>
                <td>Story</td>
                <td>Backlog Item</td>
                <td>Requirement</td>
                <td>Requirement</td>
            </tr>
            <tr>
                <td>Defect</td>
                <td>Defect</td>
                <td>Defect</td>
                <td>Defect</td>
                <td>Defect</td>
            </tr>
            <tr>
                <td>Task</td>
                <td>Task</td>
                <td>Task</td>
                <td>Task</td>
                <td>Task</td>
            </tr>
            <tr>
                <td>Test</td>
                <td>Test</td>
                <td>Test</td>
                <td>Test</td>
                <td>Test</td>
            </tr>
            <tr>
                <td>RegressionTest</td>
                <td>RegressionTest</td>
                <td>RegressionTest</td>
                <td>RegressionTest</td>
                <td>RegressionTest</td>
            </tr>
            <tr>
                <td>RegressionPlan</td>
                <td>RegressionPlan</td>
                <td>RegressionPlan</td>
                <td>RegressionPlan</td>
                <td>RegressionPlan</td>
            </tr>
            <tr>
                <td>RegressionSuite</td>
                <td>RegressionSuite</td>
                <td>RegressionSuite</td>
                <td>RegressionSuite</td>
                <td>RegressionSuite</td>
            </tr>
            <tr>
                <td>TestSet</td>
                <td>TestSet</td>
                <td>TestSet</td>
                <td>TestSet</td>
                <td>TestSet</td>
            </tr>
            <tr>
                <td>Environment</td>
                <td>Environment</td>
                <td>Environment</td>
                <td>Environment</td>
                <td>Environment</td>
            </tr>
        </tbody>
    </table>

**Asset Type.**  Asset types describe the "classes" of business data available. Asset types form an inheritance hierarchy, such that each asset type inherits attribute definitions, operations, and rules from it's "parent" asset type. Those asset types at the leaves of this hierarchy are concrete, whereas asset types with "children" asset types are abstract. Assets are all instances of concrete asset types. Asset types are identified by unique names.

By way of example, Story and Defect are concrete asset types. On the other hand, Workitem is an abstract asset type, from which Story and Defect ultimately derive.

**Attribute Definition.**  Attribute definitions describe the properties that "make up" each asset type. An attribute definition defines the type of its value, whether it is required and/or read-only, and many other qualities. Attribute definitions are identified by a name that is unique within its asset type.

Attribute definitions are defined as either scalars or relations to other assets. Further, relation attribute definitions can be either single-value or multi-value. For example, the Estimate attribute definition on the Workitem asset type is a scalar (specifically, a floating-point number). On the other hand, the Workitem asset type's Scope attribute definition is a single-value relation (to a Scope asset). The reverse relation, Workitems on the Scope asset type, is a multi-value relation (to Workitem assets).

**Asset.**  Actual business objects in VersionOne are assets, which are instances of concrete asset types. Each asset is uniquely identified by it's asset type and ID (an integer). For example, Member:20 identifies the Member asset with ID of 20.

**Attribute.**  On every asset are a number of attributes, which attach specific values to the attribute definitions defined in the asset type. If the attribute's definition is a relation, then the value(s) of the attribute are references to an asset(s).

**Moment.**  As data changes in VersionOne, a history is maintained. Every change to every asset is journaled within the system, and assigned a chronologically-increasing integer called a moment. A past version of an asset is uniquely identified by it's asset type, ID, and Moment. A past version of a relation attribute will refer to the past version of it's target asset. For example, Member:20:563 identifies the Member asset with ID of 20, as it was at the time of moment 563.