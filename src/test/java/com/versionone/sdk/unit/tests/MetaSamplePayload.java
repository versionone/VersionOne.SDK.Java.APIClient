package com.versionone.sdk.unit.tests;

public class MetaSamplePayload {

    public static final String AssetTypeType = "<AssetType name=\"AssetType\"  version=\"16.1.1.225\" token=\"AssetType\" displayname=\"AssetType'AssetType\" abstract=\"False\">" +
            "    <DefaultOrderBy href=\"/v1sdktesting/meta.v1/AssetType/Order\" tokenref=\"AssetType.Order\" />" +
            "    <DefaultDisplayBy href=\"/v1sdktesting/meta.v1/AssetType/Name\" tokenref=\"AssetType.Name\" />" +
            "    <ShortName href=\"/v1sdktesting/meta.v1/AssetType/Name\" tokenref=\"AssetType.Name\" />" +
            "    <Name href=\"/v1sdktesting/meta.v1/AssetType/Name\" tokenref=\"AssetType.Name\" />" +
            "    <AttributeDefinition name=\"ID\" token=\"AssetType.ID\" displayname=\"AttributeDefinition'ID'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/ID\" tokenref=\"AssetType.ID\" />" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ID\" tokenref=\"AssetType.ID\" />" +
            "        <DisplayByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Name\" tokenref=\"AssetType.Name\" />" +
            "        <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Now\" token=\"AssetType.Now\" displayname=\"AttributeDefinition'Now'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/ID\" tokenref=\"AssetType.ID\" />" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Now\" tokenref=\"AssetType.Now\" />" +
            "        <DisplayByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Name\" tokenref=\"AssetType.Name\" />" +
            "        <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"History\" token=\"AssetType.History\" displayname=\"AttributeDefinition'History'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/Now\" tokenref=\"AssetType.Now\" />" +
            "        <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"AssetType\" token=\"AssetType.AssetType\" displayname=\"AttributeDefinition'AssetType'AssetType\" attributetype=\"AssetType\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/AssetType\" tokenref=\"AssetType.AssetType\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Key\" token=\"AssetType.Key\" displayname=\"AttributeDefinition'Key'AssetType\" attributetype=\"Opaque\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Key\" tokenref=\"AssetType.Key\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Moment\" token=\"AssetType.Moment\" displayname=\"AttributeDefinition'Moment'AssetType\" attributetype=\"Opaque\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Moment\" tokenref=\"AssetType.Moment\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Is\" token=\"AssetType.Is\" displayname=\"AttributeDefinition'Is'AssetType\" attributetype=\"Boolean\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\" />" +
            "    <AttributeDefinition name=\"ChangeDate\" token=\"AssetType.ChangeDate\" displayname=\"AttributeDefinition'ChangeDate'AssetType\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ChangeDate\" tokenref=\"AssetType.ChangeDate\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"RetireDate\" token=\"AssetType.RetireDate\" displayname=\"AttributeDefinition'RetireDate'AssetType\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/RetireDate\" tokenref=\"AssetType.RetireDate\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"CreateDate\" token=\"AssetType.CreateDate\" displayname=\"AttributeDefinition'CreateDate'AssetType\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/CreateDate\" tokenref=\"AssetType.CreateDate\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"ChangeDateUTC\" token=\"AssetType.ChangeDateUTC\" displayname=\"AttributeDefinition'ChangeDateUTC'AssetType\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ChangeDateUTC\" tokenref=\"AssetType.ChangeDateUTC\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"RetireDateUTC\" token=\"AssetType.RetireDateUTC\" displayname=\"AttributeDefinition'RetireDateUTC'AssetType\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/RetireDateUTC\" tokenref=\"AssetType.RetireDateUTC\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Days\" token=\"AssetType.Days\" displayname=\"AttributeDefinition'Days'AssetType\" attributetype=\"Numeric\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Days\" tokenref=\"AssetType.Days\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"CreateDateUTC\" token=\"AssetType.CreateDateUTC\" displayname=\"AttributeDefinition'CreateDateUTC'AssetType\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/CreateDateUTC\" tokenref=\"AssetType.CreateDateUTC\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"ChangeReason\" token=\"AssetType.ChangeReason\" displayname=\"AttributeDefinition'ChangeReason'AssetType\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ChangeReason\" tokenref=\"AssetType.ChangeReason\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"RetireReason\" token=\"AssetType.RetireReason\" displayname=\"AttributeDefinition'RetireReason'AssetType\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/RetireReason\" tokenref=\"AssetType.RetireReason\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"CreateReason\" token=\"AssetType.CreateReason\" displayname=\"AttributeDefinition'CreateReason'AssetType\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/CreateReason\" tokenref=\"AssetType.CreateReason\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"ChangeComment\" token=\"AssetType.ChangeComment\" displayname=\"AttributeDefinition'ChangeComment'AssetType\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ChangeComment\" tokenref=\"AssetType.ChangeComment\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"RetireComment\" token=\"AssetType.RetireComment\" displayname=\"AttributeDefinition'RetireComment'AssetType\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/RetireComment\" tokenref=\"AssetType.RetireComment\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"CreateComment\" token=\"AssetType.CreateComment\" displayname=\"AttributeDefinition'CreateComment'AssetType\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/CreateComment\" tokenref=\"AssetType.CreateComment\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"ChangedBy\" token=\"AssetType.ChangedBy\" displayname=\"AttributeDefinition'ChangedBy'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ChangedBy.Name\" tokenref=\"AssetType.ChangedBy.Name\" />" +
            "        <DisplayByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ChangedBy.Name\" tokenref=\"AssetType.ChangedBy.Name\" />" +
            "        <RelatedAsset nameref=\"Member\" href=\"/v1sdktesting/meta.v1/Member\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"RetiredBy\" token=\"AssetType.RetiredBy\" displayname=\"AttributeDefinition'RetiredBy'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/RetiredBy.Name\" tokenref=\"AssetType.RetiredBy.Name\" />" +
            "        <DisplayByAttribute href=\"/v1sdktesting/meta.v1/AssetType/RetiredBy.Name\" tokenref=\"AssetType.RetiredBy.Name\" />" +
            "        <RelatedAsset nameref=\"Member\" href=\"/v1sdktesting/meta.v1/Member\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"CreatedBy\" token=\"AssetType.CreatedBy\" displayname=\"AttributeDefinition'CreatedBy'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/CreatedBy.Name\" tokenref=\"AssetType.CreatedBy.Name\" />" +
            "        <DisplayByAttribute href=\"/v1sdktesting/meta.v1/AssetType/CreatedBy.Name\" tokenref=\"AssetType.CreatedBy.Name\" />" +
            "        <RelatedAsset nameref=\"Member\" href=\"/v1sdktesting/meta.v1/Member\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Viewers\" token=\"AssetType.Viewers\" displayname=\"AttributeDefinition'Viewers'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "        <RelatedAsset nameref=\"Member\" href=\"/v1sdktesting/meta.v1/Member\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Prior\" token=\"AssetType.Prior\" displayname=\"AttributeDefinition'Prior'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Prior.Order\" tokenref=\"AssetType.Prior.Order\" />" +
            "        <DisplayByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Prior.Name\" tokenref=\"AssetType.Prior.Name\" />" +
            "        <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"VisibleToCollaborator\" token=\"AssetType.VisibleToCollaborator\" displayname=\"AttributeDefinition'VisibleToCollaborator'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/VisibleToCollaborator\" tokenref=\"AssetType.VisibleToCollaborator\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"ListValidValuesFilter\" token=\"AssetType.ListValidValuesFilter\" displayname=\"AttributeDefinition'ListValidValuesFilter'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ListValidValuesFilter\" tokenref=\"AssetType.ListValidValuesFilter\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"EventDefinitions\" token=\"AssetType.EventDefinitions\" displayname=\"AttributeDefinition'EventDefinitions'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/EventDefinition/Asset\" tokenref=\"EventDefinition.Asset\" />" +
            "        <RelatedAsset nameref=\"EventDefinition\" href=\"/v1sdktesting/meta.v1/EventDefinition\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"UseViewRightsForUpdate\" token=\"AssetType.UseViewRightsForUpdate\" displayname=\"AttributeDefinition'UseViewRightsForUpdate'AssetType\" attributetype=\"Boolean\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/UseViewRightsForUpdate\" tokenref=\"AssetType.UseViewRightsForUpdate\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"SecureViaRelation\" token=\"AssetType.SecureViaRelation\" displayname=\"AttributeDefinition'SecureViaRelation'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/SecureViaRelation\" tokenref=\"AssetType.SecureViaRelation\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"VisibleToInstigator\" token=\"AssetType.VisibleToInstigator\" displayname=\"AttributeDefinition'VisibleToInstigator'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/VisibleToInstigator\" tokenref=\"AssetType.VisibleToInstigator\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"IsCustom\" token=\"AssetType.IsCustom\" displayname=\"AttributeDefinition'IsCustom'AssetType\" attributetype=\"Boolean\" isreadonly=\"False\" isrequired=\"True\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/IsCustom\" tokenref=\"AssetType.IsCustom\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"DefaultHierarchyAttribute\" token=\"AssetType.DefaultHierarchyAttribute\" displayname=\"AttributeDefinition'DefaultHierarchyAttribute'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/DefaultHierarchyAttribute\" tokenref=\"AssetType.DefaultHierarchyAttribute\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Base\" token=\"AssetType.Base\" displayname=\"AttributeDefinition'Base'AssetType\" attributetype=\"Relation\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/AssetTypes\" tokenref=\"AssetType.AssetTypes\" />" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Base.Order\" tokenref=\"AssetType.Base.Order\" />" +
            "        <DisplayByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Base.Name\" tokenref=\"AssetType.Base.Name\" />" +
            "        <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"AssetTypes\" token=\"AssetType.AssetTypes\" displayname=\"AttributeDefinition'AssetTypes'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/Base\" tokenref=\"AssetType.Base\" />" +
            "        <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"BaseMeAndUp\" token=\"AssetType.BaseMeAndUp\" displayname=\"AttributeDefinition'BaseMeAndUp'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/AssetTypesMeAndDown\" tokenref=\"AssetType.AssetTypesMeAndDown\" />" +
            "        <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"BaseAndUp\" token=\"AssetType.BaseAndUp\" displayname=\"AttributeDefinition'BaseAndUp'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/AssetTypesAndDown\" tokenref=\"AssetType.AssetTypesAndDown\" />" +
            "        <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"BaseAndMe\" token=\"AssetType.BaseAndMe\" displayname=\"AttributeDefinition'BaseAndMe'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/AssetTypesAndMe\" tokenref=\"AssetType.AssetTypesAndMe\" />" +
            "        <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"AssetTypesMeAndDown\" token=\"AssetType.AssetTypesMeAndDown\" displayname=\"AttributeDefinition'AssetTypesMeAndDown'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/BaseMeAndUp\" tokenref=\"AssetType.BaseMeAndUp\" />" +
            "        <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"AssetTypesAndDown\" token=\"AssetType.AssetTypesAndDown\" displayname=\"AttributeDefinition'AssetTypesAndDown'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/BaseAndUp\" tokenref=\"AssetType.BaseAndUp\" />" +
            "        <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"AssetTypesAndMe\" token=\"AssetType.AssetTypesAndMe\" displayname=\"AttributeDefinition'AssetTypesAndMe'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/BaseAndMe\" tokenref=\"AssetType.BaseAndMe\" />" +
            "        <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Operations\" token=\"AssetType.Operations\" displayname=\"AttributeDefinition'Operations'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/Operation/Asset\" tokenref=\"Operation.Asset\" />" +
            "        <RelatedAsset nameref=\"Operation\" href=\"/v1sdktesting/meta.v1/Operation\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Overrides\" token=\"AssetType.Overrides\" displayname=\"AttributeDefinition'Overrides'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/Override/Asset\" tokenref=\"Override.Asset\" />" +
            "        <RelatedAsset nameref=\"Override\" href=\"/v1sdktesting/meta.v1/Override\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"AttributeDefinitions\" token=\"AssetType.AttributeDefinitions\" displayname=\"AttributeDefinition'AttributeDefinitions'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AttributeDefinition/Asset\" tokenref=\"AttributeDefinition.Asset\" />" +
            "        <RelatedAsset nameref=\"AttributeDefinition\" href=\"/v1sdktesting/meta.v1/AttributeDefinition\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Order\" token=\"AssetType.Order\" displayname=\"AttributeDefinition'Order'AssetType\" attributetype=\"Rank\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Order\" tokenref=\"AssetType.Order\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"NumberPattern\" token=\"AssetType.NumberPattern\" displayname=\"AttributeDefinition'NumberPattern'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/NumberPattern\" tokenref=\"AssetType.NumberPattern\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Resolver\" token=\"AssetType.Resolver\" displayname=\"AttributeDefinition'Resolver'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Resolver\" tokenref=\"AssetType.Resolver\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"NameResolutionAttributes\" token=\"AssetType.NameResolutionAttributes\" displayname=\"AttributeDefinition'NameResolutionAttributes'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/NameResolutionAttributes\" tokenref=\"AssetType.NameResolutionAttributes\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"DefaultOrderByAttribute\" token=\"AssetType.DefaultOrderByAttribute\" displayname=\"AttributeDefinition'DefaultOrderByAttribute'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/DefaultOrderByAttribute\" tokenref=\"AssetType.DefaultOrderByAttribute\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"UpdatePrivileges\" token=\"AssetType.UpdatePrivileges\" displayname=\"AttributeDefinition'UpdatePrivileges'AssetType\" attributetype=\"LongInt\" isreadonly=\"False\" isrequired=\"True\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/UpdatePrivileges\" tokenref=\"AssetType.UpdatePrivileges\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"NewSecurityScopeRelation\" token=\"AssetType.NewSecurityScopeRelation\" displayname=\"AttributeDefinition'NewSecurityScopeRelation'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/NewSecurityScopeRelation\" tokenref=\"AssetType.NewSecurityScopeRelation\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"SecurityScopeRelation\" token=\"AssetType.SecurityScopeRelation\" displayname=\"AttributeDefinition'SecurityScopeRelation'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/SecurityScopeRelation\" tokenref=\"AssetType.SecurityScopeRelation\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"UpdateRights\" token=\"AssetType.UpdateRights\" displayname=\"AttributeDefinition'UpdateRights'AssetType\" attributetype=\"LongInt\" isreadonly=\"False\" isrequired=\"True\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/UpdateRights\" tokenref=\"AssetType.UpdateRights\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"ViewRights\" token=\"AssetType.ViewRights\" displayname=\"AttributeDefinition'ViewRights'AssetType\" attributetype=\"LongInt\" isreadonly=\"False\" isrequired=\"True\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ViewRights\" tokenref=\"AssetType.ViewRights\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Initializer\" token=\"AssetType.Initializer\" displayname=\"AttributeDefinition'Initializer'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Initializer\" tokenref=\"AssetType.Initializer\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"ShortNameAttribute\" token=\"AssetType.ShortNameAttribute\" displayname=\"AttributeDefinition'ShortNameAttribute'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ShortNameAttribute\" tokenref=\"AssetType.ShortNameAttribute\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Name\" token=\"AssetType.Name\" displayname=\"AttributeDefinition'Name'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"True\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Name\" tokenref=\"AssetType.Name\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"IsReadOnly\" token=\"AssetType.IsReadOnly\" displayname=\"AttributeDefinition'IsReadOnly'AssetType\" attributetype=\"Boolean\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/IsReadOnly\" tokenref=\"AssetType.IsReadOnly\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"IsDeletable\" token=\"AssetType.IsDeletable\" displayname=\"AttributeDefinition'IsDeletable'AssetType\" attributetype=\"Boolean\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/IsDeletable\" tokenref=\"AssetType.IsDeletable\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"IsCanned\" token=\"AssetType.IsCanned\" displayname=\"AttributeDefinition'IsCanned'AssetType\" attributetype=\"Boolean\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/IsCanned\" tokenref=\"AssetType.IsCanned\" />" +
            "    </AttributeDefinition>" +
            "</AssetType>";

    public static final String PrimaryRelationType = "<AssetType version=\"16.1.1.225\" name=\"PrimaryRelation\" token=\"PrimaryRelation\" displayname=\"AssetType'PrimaryRelation\" abstract=\"False\">" +
            "    <DefaultOrderBy href=\"/v1sdktesting/meta.v1/PrimaryRelation/ID\" tokenref=\"PrimaryRelation.ID\" />" +
            "    <AttributeDefinition name=\"ID\" token=\"PrimaryRelation.ID\" displayname=\"AttributeDefinition'ID'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/PrimaryRelation/ID\" tokenref=\"PrimaryRelation.ID\" />" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/ID\" tokenref=\"PrimaryRelation.ID\" />" +
            "        <DisplayByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/ID\" tokenref=\"PrimaryRelation.ID\" />" +
            "        <RelatedAsset nameref=\"PrimaryRelation\" href=\"/v1sdktesting/meta.v1/PrimaryRelation\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Now\" token=\"PrimaryRelation.Now\" displayname=\"AttributeDefinition'Now'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/PrimaryRelation/ID\" tokenref=\"PrimaryRelation.ID\" />" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/Now\" tokenref=\"PrimaryRelation.Now\" />" +
            "        <DisplayByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/Now\" tokenref=\"PrimaryRelation.Now\" />" +
            "        <RelatedAsset nameref=\"PrimaryRelation\" href=\"/v1sdktesting/meta.v1/PrimaryRelation\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"History\" token=\"PrimaryRelation.History\" displayname=\"AttributeDefinition'History'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "        <ReciprocalRelation href=\"/v1sdktesting/meta.v1/PrimaryRelation/Now\" tokenref=\"PrimaryRelation.Now\" />" +
            "        <RelatedAsset nameref=\"PrimaryRelation\" href=\"/v1sdktesting/meta.v1/PrimaryRelation\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"AssetType\" token=\"PrimaryRelation.AssetType\" displayname=\"AttributeDefinition'AssetType'PrimaryRelation\" attributetype=\"AssetType\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/AssetType\" tokenref=\"PrimaryRelation.AssetType\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Key\" token=\"PrimaryRelation.Key\" displayname=\"AttributeDefinition'Key'PrimaryRelation\" attributetype=\"Opaque\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/Key\" tokenref=\"PrimaryRelation.Key\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Moment\" token=\"PrimaryRelation.Moment\" displayname=\"AttributeDefinition'Moment'PrimaryRelation\" attributetype=\"Opaque\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/Moment\" tokenref=\"PrimaryRelation.Moment\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Is\" token=\"PrimaryRelation.Is\" displayname=\"AttributeDefinition'Is'PrimaryRelation\" attributetype=\"Boolean\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\" />" +
            "    <AttributeDefinition name=\"ChangeDate\" token=\"PrimaryRelation.ChangeDate\" displayname=\"AttributeDefinition'ChangeDate'PrimaryRelation\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/ChangeDate\" tokenref=\"PrimaryRelation.ChangeDate\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"RetireDate\" token=\"PrimaryRelation.RetireDate\" displayname=\"AttributeDefinition'RetireDate'PrimaryRelation\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/RetireDate\" tokenref=\"PrimaryRelation.RetireDate\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"CreateDate\" token=\"PrimaryRelation.CreateDate\" displayname=\"AttributeDefinition'CreateDate'PrimaryRelation\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/CreateDate\" tokenref=\"PrimaryRelation.CreateDate\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"ChangeDateUTC\" token=\"PrimaryRelation.ChangeDateUTC\" displayname=\"AttributeDefinition'ChangeDateUTC'PrimaryRelation\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/ChangeDateUTC\" tokenref=\"PrimaryRelation.ChangeDateUTC\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"RetireDateUTC\" token=\"PrimaryRelation.RetireDateUTC\" displayname=\"AttributeDefinition'RetireDateUTC'PrimaryRelation\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/RetireDateUTC\" tokenref=\"PrimaryRelation.RetireDateUTC\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Days\" token=\"PrimaryRelation.Days\" displayname=\"AttributeDefinition'Days'PrimaryRelation\" attributetype=\"Numeric\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/Days\" tokenref=\"PrimaryRelation.Days\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"CreateDateUTC\" token=\"PrimaryRelation.CreateDateUTC\" displayname=\"AttributeDefinition'CreateDateUTC'PrimaryRelation\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/CreateDateUTC\" tokenref=\"PrimaryRelation.CreateDateUTC\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"ChangeReason\" token=\"PrimaryRelation.ChangeReason\" displayname=\"AttributeDefinition'ChangeReason'PrimaryRelation\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/ChangeReason\" tokenref=\"PrimaryRelation.ChangeReason\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"RetireReason\" token=\"PrimaryRelation.RetireReason\" displayname=\"AttributeDefinition'RetireReason'PrimaryRelation\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/RetireReason\" tokenref=\"PrimaryRelation.RetireReason\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"CreateReason\" token=\"PrimaryRelation.CreateReason\" displayname=\"AttributeDefinition'CreateReason'PrimaryRelation\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/CreateReason\" tokenref=\"PrimaryRelation.CreateReason\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"ChangeComment\" token=\"PrimaryRelation.ChangeComment\" displayname=\"AttributeDefinition'ChangeComment'PrimaryRelation\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/ChangeComment\" tokenref=\"PrimaryRelation.ChangeComment\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"RetireComment\" token=\"PrimaryRelation.RetireComment\" displayname=\"AttributeDefinition'RetireComment'PrimaryRelation\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/RetireComment\" tokenref=\"PrimaryRelation.RetireComment\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"CreateComment\" token=\"PrimaryRelation.CreateComment\" displayname=\"AttributeDefinition'CreateComment'PrimaryRelation\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/CreateComment\" tokenref=\"PrimaryRelation.CreateComment\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"ChangedBy\" token=\"PrimaryRelation.ChangedBy\" displayname=\"AttributeDefinition'ChangedBy'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/ChangedBy.Name\" tokenref=\"PrimaryRelation.ChangedBy.Name\" />" +
            "        <DisplayByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/ChangedBy.Name\" tokenref=\"PrimaryRelation.ChangedBy.Name\" />" +
            "        <RelatedAsset nameref=\"Member\" href=\"/v1sdktesting/meta.v1/Member\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"RetiredBy\" token=\"PrimaryRelation.RetiredBy\" displayname=\"AttributeDefinition'RetiredBy'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/RetiredBy.Name\" tokenref=\"PrimaryRelation.RetiredBy.Name\" />" +
            "        <DisplayByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/RetiredBy.Name\" tokenref=\"PrimaryRelation.RetiredBy.Name\" />" +
            "        <RelatedAsset nameref=\"Member\" href=\"/v1sdktesting/meta.v1/Member\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"CreatedBy\" token=\"PrimaryRelation.CreatedBy\" displayname=\"AttributeDefinition'CreatedBy'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/CreatedBy.Name\" tokenref=\"PrimaryRelation.CreatedBy.Name\" />" +
            "        <DisplayByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/CreatedBy.Name\" tokenref=\"PrimaryRelation.CreatedBy.Name\" />" +
            "        <RelatedAsset nameref=\"Member\" href=\"/v1sdktesting/meta.v1/Member\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Viewers\" token=\"PrimaryRelation.Viewers\" displayname=\"AttributeDefinition'Viewers'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "        <RelatedAsset nameref=\"Member\" href=\"/v1sdktesting/meta.v1/Member\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Prior\" token=\"PrimaryRelation.Prior\" displayname=\"AttributeDefinition'Prior'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/Prior.ID\" tokenref=\"PrimaryRelation.Prior.ID\" />" +
            "        <DisplayByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/Prior\" tokenref=\"PrimaryRelation.Prior\" />" +
            "        <RelatedAsset nameref=\"PrimaryRelation\" href=\"/v1sdktesting/meta.v1/PrimaryRelation\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"To\" token=\"PrimaryRelation.To\" displayname=\"AttributeDefinition'To'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"False\" isrequired=\"True\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/To.Order\" tokenref=\"PrimaryRelation.To.Order\" />" +
            "        <DisplayByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/To.Name\" tokenref=\"PrimaryRelation.To.Name\" />" +
            "        <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"From\" token=\"PrimaryRelation.From\" displayname=\"AttributeDefinition'From'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"False\" isrequired=\"True\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/From.Order\" tokenref=\"PrimaryRelation.From.Order\" />" +
            "        <DisplayByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/From.Name\" tokenref=\"PrimaryRelation.From.Name\" />" +
            "        <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "    </AttributeDefinition>" +
            "    <AttributeDefinition name=\"Relation\" token=\"PrimaryRelation.Relation\" displayname=\"AttributeDefinition'Relation'PrimaryRelation\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"True\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "        <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/Relation\" tokenref=\"PrimaryRelation.Relation\" />" +
            "    </AttributeDefinition>" +
            "</AssetType>";

    public static final String FullSubset = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<Meta href=\"/v1sdktesting/meta.v1/\" version=\"16.1.1.225\">" +
            "    <AssetType name=\"AssetType\" token=\"AssetType\" displayname=\"AssetType'AssetType\" abstract=\"False\">" +
            "        <DefaultOrderBy href=\"/v1sdktesting/meta.v1/AssetType/Order\" tokenref=\"AssetType.Order\" />" +
            "        <DefaultDisplayBy href=\"/v1sdktesting/meta.v1/AssetType/Name\" tokenref=\"AssetType.Name\" />" +
            "        <ShortName href=\"/v1sdktesting/meta.v1/AssetType/Name\" tokenref=\"AssetType.Name\" />" +
            "        <Name href=\"/v1sdktesting/meta.v1/AssetType/Name\" tokenref=\"AssetType.Name\" />" +
            "        <AttributeDefinition name=\"ID\" token=\"AssetType.ID\" displayname=\"AttributeDefinition'ID'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/ID\" tokenref=\"AssetType.ID\" />" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ID\" tokenref=\"AssetType.ID\" />" +
            "            <DisplayByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Name\" tokenref=\"AssetType.Name\" />" +
            "            <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Now\" token=\"AssetType.Now\" displayname=\"AttributeDefinition'Now'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/ID\" tokenref=\"AssetType.ID\" />" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Now\" tokenref=\"AssetType.Now\" />" +
            "            <DisplayByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Name\" tokenref=\"AssetType.Name\" />" +
            "            <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"History\" token=\"AssetType.History\" displayname=\"AttributeDefinition'History'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/Now\" tokenref=\"AssetType.Now\" />" +
            "            <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"AssetType\" token=\"AssetType.AssetType\" displayname=\"AttributeDefinition'AssetType'AssetType\" attributetype=\"AssetType\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/AssetType\" tokenref=\"AssetType.AssetType\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Key\" token=\"AssetType.Key\" displayname=\"AttributeDefinition'Key'AssetType\" attributetype=\"Opaque\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Key\" tokenref=\"AssetType.Key\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Moment\" token=\"AssetType.Moment\" displayname=\"AttributeDefinition'Moment'AssetType\" attributetype=\"Opaque\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Moment\" tokenref=\"AssetType.Moment\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Is\" token=\"AssetType.Is\" displayname=\"AttributeDefinition'Is'AssetType\" attributetype=\"Boolean\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\" />" +
            "        <AttributeDefinition name=\"ChangeDate\" token=\"AssetType.ChangeDate\" displayname=\"AttributeDefinition'ChangeDate'AssetType\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ChangeDate\" tokenref=\"AssetType.ChangeDate\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"RetireDate\" token=\"AssetType.RetireDate\" displayname=\"AttributeDefinition'RetireDate'AssetType\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/RetireDate\" tokenref=\"AssetType.RetireDate\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"CreateDate\" token=\"AssetType.CreateDate\" displayname=\"AttributeDefinition'CreateDate'AssetType\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/CreateDate\" tokenref=\"AssetType.CreateDate\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"ChangeDateUTC\" token=\"AssetType.ChangeDateUTC\" displayname=\"AttributeDefinition'ChangeDateUTC'AssetType\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ChangeDateUTC\" tokenref=\"AssetType.ChangeDateUTC\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"RetireDateUTC\" token=\"AssetType.RetireDateUTC\" displayname=\"AttributeDefinition'RetireDateUTC'AssetType\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/RetireDateUTC\" tokenref=\"AssetType.RetireDateUTC\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Days\" token=\"AssetType.Days\" displayname=\"AttributeDefinition'Days'AssetType\" attributetype=\"Numeric\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Days\" tokenref=\"AssetType.Days\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"CreateDateUTC\" token=\"AssetType.CreateDateUTC\" displayname=\"AttributeDefinition'CreateDateUTC'AssetType\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/CreateDateUTC\" tokenref=\"AssetType.CreateDateUTC\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"ChangeReason\" token=\"AssetType.ChangeReason\" displayname=\"AttributeDefinition'ChangeReason'AssetType\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ChangeReason\" tokenref=\"AssetType.ChangeReason\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"RetireReason\" token=\"AssetType.RetireReason\" displayname=\"AttributeDefinition'RetireReason'AssetType\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/RetireReason\" tokenref=\"AssetType.RetireReason\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"CreateReason\" token=\"AssetType.CreateReason\" displayname=\"AttributeDefinition'CreateReason'AssetType\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/CreateReason\" tokenref=\"AssetType.CreateReason\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"ChangeComment\" token=\"AssetType.ChangeComment\" displayname=\"AttributeDefinition'ChangeComment'AssetType\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ChangeComment\" tokenref=\"AssetType.ChangeComment\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"RetireComment\" token=\"AssetType.RetireComment\" displayname=\"AttributeDefinition'RetireComment'AssetType\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/RetireComment\" tokenref=\"AssetType.RetireComment\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"CreateComment\" token=\"AssetType.CreateComment\" displayname=\"AttributeDefinition'CreateComment'AssetType\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/CreateComment\" tokenref=\"AssetType.CreateComment\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"ChangedBy\" token=\"AssetType.ChangedBy\" displayname=\"AttributeDefinition'ChangedBy'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ChangedBy.Name\" tokenref=\"AssetType.ChangedBy.Name\" />" +
            "            <DisplayByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ChangedBy.Name\" tokenref=\"AssetType.ChangedBy.Name\" />" +
            "            <RelatedAsset nameref=\"Member\" href=\"/v1sdktesting/meta.v1/Member\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"RetiredBy\" token=\"AssetType.RetiredBy\" displayname=\"AttributeDefinition'RetiredBy'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/RetiredBy.Name\" tokenref=\"AssetType.RetiredBy.Name\" />" +
            "            <DisplayByAttribute href=\"/v1sdktesting/meta.v1/AssetType/RetiredBy.Name\" tokenref=\"AssetType.RetiredBy.Name\" />" +
            "            <RelatedAsset nameref=\"Member\" href=\"/v1sdktesting/meta.v1/Member\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"CreatedBy\" token=\"AssetType.CreatedBy\" displayname=\"AttributeDefinition'CreatedBy'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/CreatedBy.Name\" tokenref=\"AssetType.CreatedBy.Name\" />" +
            "            <DisplayByAttribute href=\"/v1sdktesting/meta.v1/AssetType/CreatedBy.Name\" tokenref=\"AssetType.CreatedBy.Name\" />" +
            "            <RelatedAsset nameref=\"Member\" href=\"/v1sdktesting/meta.v1/Member\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Viewers\" token=\"AssetType.Viewers\" displayname=\"AttributeDefinition'Viewers'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "            <RelatedAsset nameref=\"Member\" href=\"/v1sdktesting/meta.v1/Member\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Prior\" token=\"AssetType.Prior\" displayname=\"AttributeDefinition'Prior'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Prior.Order\" tokenref=\"AssetType.Prior.Order\" />" +
            "            <DisplayByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Prior.Name\" tokenref=\"AssetType.Prior.Name\" />" +
            "            <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"VisibleToCollaborator\" token=\"AssetType.VisibleToCollaborator\" displayname=\"AttributeDefinition'VisibleToCollaborator'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/VisibleToCollaborator\" tokenref=\"AssetType.VisibleToCollaborator\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"ListValidValuesFilter\" token=\"AssetType.ListValidValuesFilter\" displayname=\"AttributeDefinition'ListValidValuesFilter'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ListValidValuesFilter\" tokenref=\"AssetType.ListValidValuesFilter\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"EventDefinitions\" token=\"AssetType.EventDefinitions\" displayname=\"AttributeDefinition'EventDefinitions'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/EventDefinition/Asset\" tokenref=\"EventDefinition.Asset\" />" +
            "            <RelatedAsset nameref=\"EventDefinition\" href=\"/v1sdktesting/meta.v1/EventDefinition\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"UseViewRightsForUpdate\" token=\"AssetType.UseViewRightsForUpdate\" displayname=\"AttributeDefinition'UseViewRightsForUpdate'AssetType\" attributetype=\"Boolean\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/UseViewRightsForUpdate\" tokenref=\"AssetType.UseViewRightsForUpdate\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"SecureViaRelation\" token=\"AssetType.SecureViaRelation\" displayname=\"AttributeDefinition'SecureViaRelation'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/SecureViaRelation\" tokenref=\"AssetType.SecureViaRelation\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"VisibleToInstigator\" token=\"AssetType.VisibleToInstigator\" displayname=\"AttributeDefinition'VisibleToInstigator'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/VisibleToInstigator\" tokenref=\"AssetType.VisibleToInstigator\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"IsCustom\" token=\"AssetType.IsCustom\" displayname=\"AttributeDefinition'IsCustom'AssetType\" attributetype=\"Boolean\" isreadonly=\"False\" isrequired=\"True\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/IsCustom\" tokenref=\"AssetType.IsCustom\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"DefaultHierarchyAttribute\" token=\"AssetType.DefaultHierarchyAttribute\" displayname=\"AttributeDefinition'DefaultHierarchyAttribute'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/DefaultHierarchyAttribute\" tokenref=\"AssetType.DefaultHierarchyAttribute\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Base\" token=\"AssetType.Base\" displayname=\"AttributeDefinition'Base'AssetType\" attributetype=\"Relation\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/AssetTypes\" tokenref=\"AssetType.AssetTypes\" />" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Base.Order\" tokenref=\"AssetType.Base.Order\" />" +
            "            <DisplayByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Base.Name\" tokenref=\"AssetType.Base.Name\" />" +
            "            <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"AssetTypes\" token=\"AssetType.AssetTypes\" displayname=\"AttributeDefinition'AssetTypes'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/Base\" tokenref=\"AssetType.Base\" />" +
            "            <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"BaseMeAndUp\" token=\"AssetType.BaseMeAndUp\" displayname=\"AttributeDefinition'BaseMeAndUp'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/AssetTypesMeAndDown\" tokenref=\"AssetType.AssetTypesMeAndDown\" />" +
            "            <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"BaseAndUp\" token=\"AssetType.BaseAndUp\" displayname=\"AttributeDefinition'BaseAndUp'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/AssetTypesAndDown\" tokenref=\"AssetType.AssetTypesAndDown\" />" +
            "            <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"BaseAndMe\" token=\"AssetType.BaseAndMe\" displayname=\"AttributeDefinition'BaseAndMe'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/AssetTypesAndMe\" tokenref=\"AssetType.AssetTypesAndMe\" />" +
            "            <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"AssetTypesMeAndDown\" token=\"AssetType.AssetTypesMeAndDown\" displayname=\"AttributeDefinition'AssetTypesMeAndDown'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/BaseMeAndUp\" tokenref=\"AssetType.BaseMeAndUp\" />" +
            "            <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"AssetTypesAndDown\" token=\"AssetType.AssetTypesAndDown\" displayname=\"AttributeDefinition'AssetTypesAndDown'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/BaseAndUp\" tokenref=\"AssetType.BaseAndUp\" />" +
            "            <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"AssetTypesAndMe\" token=\"AssetType.AssetTypesAndMe\" displayname=\"AttributeDefinition'AssetTypesAndMe'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AssetType/BaseAndMe\" tokenref=\"AssetType.BaseAndMe\" />" +
            "            <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Operations\" token=\"AssetType.Operations\" displayname=\"AttributeDefinition'Operations'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/Operation/Asset\" tokenref=\"Operation.Asset\" />" +
            "            <RelatedAsset nameref=\"Operation\" href=\"/v1sdktesting/meta.v1/Operation\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Overrides\" token=\"AssetType.Overrides\" displayname=\"AttributeDefinition'Overrides'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/Override/Asset\" tokenref=\"Override.Asset\" />" +
            "            <RelatedAsset nameref=\"Override\" href=\"/v1sdktesting/meta.v1/Override\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"AttributeDefinitions\" token=\"AssetType.AttributeDefinitions\" displayname=\"AttributeDefinition'AttributeDefinitions'AssetType\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/AttributeDefinition/Asset\" tokenref=\"AttributeDefinition.Asset\" />" +
            "            <RelatedAsset nameref=\"AttributeDefinition\" href=\"/v1sdktesting/meta.v1/AttributeDefinition\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Order\" token=\"AssetType.Order\" displayname=\"AttributeDefinition'Order'AssetType\" attributetype=\"Rank\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Order\" tokenref=\"AssetType.Order\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"NumberPattern\" token=\"AssetType.NumberPattern\" displayname=\"AttributeDefinition'NumberPattern'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/NumberPattern\" tokenref=\"AssetType.NumberPattern\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Resolver\" token=\"AssetType.Resolver\" displayname=\"AttributeDefinition'Resolver'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Resolver\" tokenref=\"AssetType.Resolver\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"NameResolutionAttributes\" token=\"AssetType.NameResolutionAttributes\" displayname=\"AttributeDefinition'NameResolutionAttributes'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/NameResolutionAttributes\" tokenref=\"AssetType.NameResolutionAttributes\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"DefaultOrderByAttribute\" token=\"AssetType.DefaultOrderByAttribute\" displayname=\"AttributeDefinition'DefaultOrderByAttribute'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/DefaultOrderByAttribute\" tokenref=\"AssetType.DefaultOrderByAttribute\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"UpdatePrivileges\" token=\"AssetType.UpdatePrivileges\" displayname=\"AttributeDefinition'UpdatePrivileges'AssetType\" attributetype=\"LongInt\" isreadonly=\"False\" isrequired=\"True\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/UpdatePrivileges\" tokenref=\"AssetType.UpdatePrivileges\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"NewSecurityScopeRelation\" token=\"AssetType.NewSecurityScopeRelation\" displayname=\"AttributeDefinition'NewSecurityScopeRelation'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/NewSecurityScopeRelation\" tokenref=\"AssetType.NewSecurityScopeRelation\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"SecurityScopeRelation\" token=\"AssetType.SecurityScopeRelation\" displayname=\"AttributeDefinition'SecurityScopeRelation'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/SecurityScopeRelation\" tokenref=\"AssetType.SecurityScopeRelation\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"UpdateRights\" token=\"AssetType.UpdateRights\" displayname=\"AttributeDefinition'UpdateRights'AssetType\" attributetype=\"LongInt\" isreadonly=\"False\" isrequired=\"True\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/UpdateRights\" tokenref=\"AssetType.UpdateRights\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"ViewRights\" token=\"AssetType.ViewRights\" displayname=\"AttributeDefinition'ViewRights'AssetType\" attributetype=\"LongInt\" isreadonly=\"False\" isrequired=\"True\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ViewRights\" tokenref=\"AssetType.ViewRights\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Initializer\" token=\"AssetType.Initializer\" displayname=\"AttributeDefinition'Initializer'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Initializer\" tokenref=\"AssetType.Initializer\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"ShortNameAttribute\" token=\"AssetType.ShortNameAttribute\" displayname=\"AttributeDefinition'ShortNameAttribute'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/ShortNameAttribute\" tokenref=\"AssetType.ShortNameAttribute\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Name\" token=\"AssetType.Name\" displayname=\"AttributeDefinition'Name'AssetType\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"True\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/Name\" tokenref=\"AssetType.Name\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"IsReadOnly\" token=\"AssetType.IsReadOnly\" displayname=\"AttributeDefinition'IsReadOnly'AssetType\" attributetype=\"Boolean\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/IsReadOnly\" tokenref=\"AssetType.IsReadOnly\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"IsDeletable\" token=\"AssetType.IsDeletable\" displayname=\"AttributeDefinition'IsDeletable'AssetType\" attributetype=\"Boolean\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/IsDeletable\" tokenref=\"AssetType.IsDeletable\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"IsCanned\" token=\"AssetType.IsCanned\" displayname=\"AttributeDefinition'IsCanned'AssetType\" attributetype=\"Boolean\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/AssetType/IsCanned\" tokenref=\"AssetType.IsCanned\" />" +
            "        </AttributeDefinition>" +
            "    </AssetType>" +
            "    <AssetType name=\"PrimaryRelation\" token=\"PrimaryRelation\" displayname=\"AssetType'PrimaryRelation\" abstract=\"False\">" +
            "        <DefaultOrderBy href=\"/v1sdktesting/meta.v1/PrimaryRelation/ID\" tokenref=\"PrimaryRelation.ID\" />" +
            "        <AttributeDefinition name=\"ID\" token=\"PrimaryRelation.ID\" displayname=\"AttributeDefinition'ID'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/PrimaryRelation/ID\" tokenref=\"PrimaryRelation.ID\" />" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/ID\" tokenref=\"PrimaryRelation.ID\" />" +
            "            <DisplayByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/ID\" tokenref=\"PrimaryRelation.ID\" />" +
            "            <RelatedAsset nameref=\"PrimaryRelation\" href=\"/v1sdktesting/meta.v1/PrimaryRelation\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Now\" token=\"PrimaryRelation.Now\" displayname=\"AttributeDefinition'Now'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/PrimaryRelation/ID\" tokenref=\"PrimaryRelation.ID\" />" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/Now\" tokenref=\"PrimaryRelation.Now\" />" +
            "            <DisplayByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/Now\" tokenref=\"PrimaryRelation.Now\" />" +
            "            <RelatedAsset nameref=\"PrimaryRelation\" href=\"/v1sdktesting/meta.v1/PrimaryRelation\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"History\" token=\"PrimaryRelation.History\" displayname=\"AttributeDefinition'History'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "            <ReciprocalRelation href=\"/v1sdktesting/meta.v1/PrimaryRelation/Now\" tokenref=\"PrimaryRelation.Now\" />" +
            "            <RelatedAsset nameref=\"PrimaryRelation\" href=\"/v1sdktesting/meta.v1/PrimaryRelation\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"AssetType\" token=\"PrimaryRelation.AssetType\" displayname=\"AttributeDefinition'AssetType'PrimaryRelation\" attributetype=\"AssetType\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/AssetType\" tokenref=\"PrimaryRelation.AssetType\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Key\" token=\"PrimaryRelation.Key\" displayname=\"AttributeDefinition'Key'PrimaryRelation\" attributetype=\"Opaque\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/Key\" tokenref=\"PrimaryRelation.Key\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Moment\" token=\"PrimaryRelation.Moment\" displayname=\"AttributeDefinition'Moment'PrimaryRelation\" attributetype=\"Opaque\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/Moment\" tokenref=\"PrimaryRelation.Moment\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Is\" token=\"PrimaryRelation.Is\" displayname=\"AttributeDefinition'Is'PrimaryRelation\" attributetype=\"Boolean\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\" />" +
            "        <AttributeDefinition name=\"ChangeDate\" token=\"PrimaryRelation.ChangeDate\" displayname=\"AttributeDefinition'ChangeDate'PrimaryRelation\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/ChangeDate\" tokenref=\"PrimaryRelation.ChangeDate\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"RetireDate\" token=\"PrimaryRelation.RetireDate\" displayname=\"AttributeDefinition'RetireDate'PrimaryRelation\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/RetireDate\" tokenref=\"PrimaryRelation.RetireDate\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"CreateDate\" token=\"PrimaryRelation.CreateDate\" displayname=\"AttributeDefinition'CreateDate'PrimaryRelation\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/CreateDate\" tokenref=\"PrimaryRelation.CreateDate\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"ChangeDateUTC\" token=\"PrimaryRelation.ChangeDateUTC\" displayname=\"AttributeDefinition'ChangeDateUTC'PrimaryRelation\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/ChangeDateUTC\" tokenref=\"PrimaryRelation.ChangeDateUTC\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"RetireDateUTC\" token=\"PrimaryRelation.RetireDateUTC\" displayname=\"AttributeDefinition'RetireDateUTC'PrimaryRelation\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/RetireDateUTC\" tokenref=\"PrimaryRelation.RetireDateUTC\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Days\" token=\"PrimaryRelation.Days\" displayname=\"AttributeDefinition'Days'PrimaryRelation\" attributetype=\"Numeric\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/Days\" tokenref=\"PrimaryRelation.Days\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"CreateDateUTC\" token=\"PrimaryRelation.CreateDateUTC\" displayname=\"AttributeDefinition'CreateDateUTC'PrimaryRelation\" attributetype=\"Date\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/CreateDateUTC\" tokenref=\"PrimaryRelation.CreateDateUTC\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"ChangeReason\" token=\"PrimaryRelation.ChangeReason\" displayname=\"AttributeDefinition'ChangeReason'PrimaryRelation\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/ChangeReason\" tokenref=\"PrimaryRelation.ChangeReason\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"RetireReason\" token=\"PrimaryRelation.RetireReason\" displayname=\"AttributeDefinition'RetireReason'PrimaryRelation\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/RetireReason\" tokenref=\"PrimaryRelation.RetireReason\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"CreateReason\" token=\"PrimaryRelation.CreateReason\" displayname=\"AttributeDefinition'CreateReason'PrimaryRelation\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/CreateReason\" tokenref=\"PrimaryRelation.CreateReason\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"ChangeComment\" token=\"PrimaryRelation.ChangeComment\" displayname=\"AttributeDefinition'ChangeComment'PrimaryRelation\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/ChangeComment\" tokenref=\"PrimaryRelation.ChangeComment\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"RetireComment\" token=\"PrimaryRelation.RetireComment\" displayname=\"AttributeDefinition'RetireComment'PrimaryRelation\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/RetireComment\" tokenref=\"PrimaryRelation.RetireComment\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"CreateComment\" token=\"PrimaryRelation.CreateComment\" displayname=\"AttributeDefinition'CreateComment'PrimaryRelation\" attributetype=\"Text\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/CreateComment\" tokenref=\"PrimaryRelation.CreateComment\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"ChangedBy\" token=\"PrimaryRelation.ChangedBy\" displayname=\"AttributeDefinition'ChangedBy'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/ChangedBy.Name\" tokenref=\"PrimaryRelation.ChangedBy.Name\" />" +
            "            <DisplayByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/ChangedBy.Name\" tokenref=\"PrimaryRelation.ChangedBy.Name\" />" +
            "            <RelatedAsset nameref=\"Member\" href=\"/v1sdktesting/meta.v1/Member\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"RetiredBy\" token=\"PrimaryRelation.RetiredBy\" displayname=\"AttributeDefinition'RetiredBy'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/RetiredBy.Name\" tokenref=\"PrimaryRelation.RetiredBy.Name\" />" +
            "            <DisplayByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/RetiredBy.Name\" tokenref=\"PrimaryRelation.RetiredBy.Name\" />" +
            "            <RelatedAsset nameref=\"Member\" href=\"/v1sdktesting/meta.v1/Member\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"CreatedBy\" token=\"PrimaryRelation.CreatedBy\" displayname=\"AttributeDefinition'CreatedBy'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/CreatedBy.Name\" tokenref=\"PrimaryRelation.CreatedBy.Name\" />" +
            "            <DisplayByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/CreatedBy.Name\" tokenref=\"PrimaryRelation.CreatedBy.Name\" />" +
            "            <RelatedAsset nameref=\"Member\" href=\"/v1sdktesting/meta.v1/Member\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Viewers\" token=\"PrimaryRelation.Viewers\" displayname=\"AttributeDefinition'Viewers'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"True\" iscanned=\"True\" iscustom=\"False\">" +
            "            <RelatedAsset nameref=\"Member\" href=\"/v1sdktesting/meta.v1/Member\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Prior\" token=\"PrimaryRelation.Prior\" displayname=\"AttributeDefinition'Prior'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"True\" isrequired=\"False\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/Prior.ID\" tokenref=\"PrimaryRelation.Prior.ID\" />" +
            "            <DisplayByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/Prior\" tokenref=\"PrimaryRelation.Prior\" />" +
            "            <RelatedAsset nameref=\"PrimaryRelation\" href=\"/v1sdktesting/meta.v1/PrimaryRelation\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"To\" token=\"PrimaryRelation.To\" displayname=\"AttributeDefinition'To'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"False\" isrequired=\"True\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/To.Order\" tokenref=\"PrimaryRelation.To.Order\" />" +
            "            <DisplayByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/To.Name\" tokenref=\"PrimaryRelation.To.Name\" />" +
            "            <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"From\" token=\"PrimaryRelation.From\" displayname=\"AttributeDefinition'From'PrimaryRelation\" attributetype=\"Relation\" isreadonly=\"False\" isrequired=\"True\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/From.Order\" tokenref=\"PrimaryRelation.From.Order\" />" +
            "            <DisplayByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/From.Name\" tokenref=\"PrimaryRelation.From.Name\" />" +
            "            <RelatedAsset nameref=\"AssetType\" href=\"/v1sdktesting/meta.v1/AssetType\" />" +
            "        </AttributeDefinition>" +
            "        <AttributeDefinition name=\"Relation\" token=\"PrimaryRelation.Relation\" displayname=\"AttributeDefinition'Relation'PrimaryRelation\" attributetype=\"Text\" isreadonly=\"False\" isrequired=\"True\" ismultivalue=\"False\" iscanned=\"True\" iscustom=\"False\">" +
            "            <OrderByAttribute href=\"/v1sdktesting/meta.v1/PrimaryRelation/Relation\" tokenref=\"PrimaryRelation.Relation\" />" +
            "        </AttributeDefinition>" +
            "    </AssetType>" +
            "</Meta>";
}
